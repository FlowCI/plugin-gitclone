/*
 * Copyright 2017 flow.ci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flow.plugins.gitclone.util;

import com.flow.plugins.gitclone.exception.PluginException;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.util.FS;

/**
 * @author yh@fir.im
 */
public class GitUtil {

    private final static String ORIGIN = "origin";

    public static Path gitClone(String gitUrl, Path privateKeyPath, String branch, Path targetFolder,
                                ProgressMonitor processMonitor) {
        try {

            Path gitPath = getGitPath(gitUrl, targetFolder);

            // if exists delete
            if (gitPath.toFile().exists()) {
                FileUtils.deleteDirectory(gitPath.toFile());
            }

            // init bare git project
            Git.init().setDirectory(gitPath.toFile()).call();

            // set remote
            Git git = Git.open(gitPath.toFile());
            StoredConfig config = git.getRepository().getConfig();
            config.setString("remote", ORIGIN, "url", gitUrl);
            config.save();

            // pull code
            git
                .fetch()
                .setRemote(ORIGIN)
                .setTransportConfigCallback(createSessionFactory(privateKeyPath.toString()))
                .setRefSpecs(new RefSpec("refs/heads/" + branch + ":refs/heads/" + branch))
                .setProgressMonitor(processMonitor)
                .call();

            // checkout branch
            git
                .checkout()
                .setName(branch)
                .setForce(true)
                .call();

        } catch (Throwable e) {
            throw new PluginException("Git clone happens some questions " + e.getMessage());
        }
        return targetFolder;
    }

    private static Path getGitPath(String gitUrl, Path targetFolder) {
        return Paths.get(targetFolder.toString(), validateGitAndGetName(gitUrl));
    }

    private static String validateGitAndGetName(String gitUrl) {
        // verify git url
        int dotGitIndex = gitUrl.lastIndexOf(".git");
        if (dotGitIndex == -1) {
            throw new IllegalArgumentException("Illegal git url");
        }

        int lastSlashIndex = gitUrl.lastIndexOf('/');
        if (lastSlashIndex == -1) {
            throw new IllegalArgumentException("Illegal git url");
        }

        if (lastSlashIndex >= dotGitIndex) {
            throw new IllegalArgumentException("Illegal git url");
        }

        String repoName = gitUrl.substring(lastSlashIndex + 1, dotGitIndex);

        return repoName;
    }

    private static TransportConfigCallback createSessionFactory(String privateKeyPath) {
        JschConfigSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(Host host, Session session) {
                session.setConfig("StrictHostKeyChecking", "no");
            }

            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch jSch = super.createDefaultJSch(fs);

                // apply customized private key
                if (privateKeyPath != null) {
                    jSch.removeAllIdentity();
                    jSch.addIdentity(privateKeyPath.toString());
                }

                return jSch;
            }
        };

        return transport -> {
            SshTransport sshTransport = (SshTransport) transport;
            sshTransport.setSshSessionFactory(sshSessionFactory);
        };
    }
}
