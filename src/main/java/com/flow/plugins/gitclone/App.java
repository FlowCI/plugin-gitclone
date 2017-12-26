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

import com.flow.plugins.gitclone.domain.Setting;
import com.flow.plugins.gitclone.exception.PluginException;
import com.flow.plugins.gitclone.util.CommonUtil;
import com.google.common.base.Strings;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author yh@fir.im
 */
public class App {

    private final static Logger LOGGER = new Logger(App.class);

    public final static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private final static String PLUGIN_GIT_BRANCH = "PLUGIN_GIT_BRANCH";

    private final static String FLOW_GIT_BRANCH = "FLOW_GIT_BRANCH";

    private final static String FLOW_GIT_URL = "FLOW_GIT_URL";

    private final static String PLUGIN_GIT_WORKSPACE = "PLUGIN_GIT_WORKSPACE";

    private final static String PLUGIN_TOKEN = "PLUGIN_TOKEN";

    private final static String PLUGIN_API = "PLUGIN_API";

    private final static String PLUGIN_GIT_URL = "PLUGIN_GIT_URL";

    private final static RsaHelper rsaHelper = new RsaHelper();

    private final static GitHelper gitHelper = new GitHelper();

    public static void main(String[] args) {

        // init environments
        initSettings();

        System.out.println(CommonUtil.showJfigletMessage("GIT CLONE FINISH"));

        // download rsa zip
        rsaHelper
            .downloadRsaAndUnzip(
                rsaDownloadUrl(),
                workspacePath());

        // git clone
        gitHelper.fetchCode(
            Setting.getInstance().getPluginGitUrl(),
            rsaHelper.privateKeyPath(workspacePath()),
            Setting.getInstance().getPluginGitBranch(),
            workspacePath()
        );

        System.out.println(CommonUtil.showJfigletMessage("GIT CLONE FINISH"));
    }

    private static String rsaDownloadUrl() {
        return Setting.getInstance().getPluginApi() + "/credentials/" + System.getenv("FLOW_NAME") + "/download";
    }

    private static Path workspacePath() {
        try {
            Path path = Paths.get(Setting.getInstance().getPluginGitWorkspace());
            if (!path.toFile().exists()) {
                Files.createDirectories(path);
            }

            return path;
        } catch (IOException e) {
            throw new PluginException("Create workspace is error " + e.getMessage());
        }
    }

    private static void initSettings() {

        LOGGER.trace("GIT_BRANCH: " + gitBranch());
        Setting.getInstance().setPluginGitBranch(gitBranch());

        LOGGER.trace("GIT_URL: " + gitUrl());
        Setting.getInstance().setPluginGitUrl(gitUrl());

        LOGGER.trace("GIT_WORKSPACE:" + gitWorkspace());
        Setting.getInstance().setPluginGitWorkspace(gitWorkspace());

        LOGGER.trace("GIT_PLUGIN_API:" + getPluginApi());
        Setting.getInstance().setPluginApi(getPluginApi());

        Setting.getInstance().setPluginToken(System.getenv(PLUGIN_TOKEN));
    }

    private static String gitBranch() {
        String branch = System.getenv(PLUGIN_GIT_BRANCH);
        if (Strings.isNullOrEmpty(branch)) {
            return System.getenv(FLOW_GIT_BRANCH);
        }

        return branch;
    }

    private static String gitUrl() {
        String gitUrl = System.getenv(PLUGIN_GIT_URL);
        if (Strings.isNullOrEmpty(gitUrl)) {
            return System.getenv(FLOW_GIT_URL);
        }

        return gitUrl;
    }

    private static String gitWorkspace() {
        String workspace = System.getenv(PLUGIN_GIT_WORKSPACE);
        if (Strings.isNullOrEmpty(workspace)) {
            return System.getenv("HOME");
        }
        return workspace;
    }

    private static String getPluginApi() {
        String pluginApi = System.getenv(PLUGIN_API);
        if (Strings.isNullOrEmpty(pluginApi)) {
            System.out.println("PLUGIN_API is required");
            throw new PluginException("PLUGIN_API is required");
        }

        return pluginApi;
    }


}
