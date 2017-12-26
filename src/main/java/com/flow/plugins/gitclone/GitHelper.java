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

package com.flow.plugins.gitclone;

import com.flow.platform.util.Logger;
import com.flow.platform.util.git.GitException;
import com.flow.platform.util.git.GitSshClient;
import com.flow.plugins.gitclone.exception.PluginException;
import java.nio.file.Path;

/**
 * @author yh@fir.im
 */
public class GitHelper {

    private final static Logger LOGGER = new Logger(GitHelper.class);

    public void GitHelper() {
    }

    public void fetchCode(String gitUrl, Path privateKeyPath, String branch, Path targetFolder) {
        GitSshClient gitSshClient = new GitSshClient(gitUrl, privateKeyPath, targetFolder);

        try {
            gitSshClient.clone(branch, false);
        } catch (GitException e) {
            LOGGER.trace("Fetch Code Error " + e.getMessage());
            throw new PluginException("Fetch Code Error " + e.getMessage());
        }
    }
}