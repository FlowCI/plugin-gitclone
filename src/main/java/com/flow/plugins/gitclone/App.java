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
import com.flow.plugins.gitclone.domain.Setting;
import com.flow.plugins.gitclone.exception.PluginException;
import com.flow.plugins.gitclone.util.CommonUtil;
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

    private final static String PLUGIN_GIT_WORKSPACE = "PLUGIN_GIT_WORKSPACE";

    private final static String PLUGIN_TOKEN = "PLUGIN_TOKEN";

    private final static String PLUGIN_API = "PLUGIN_API";

    private final static String PLUGIN_GIT_URL = "PLUGIN_GIT_URL";

    private final static RsaHelper rsaHelper = new RsaHelper();

    private final static GitHelper gitHelper = new GitHelper();

    public static void main(String[] args) {

        CommonUtil.showJfigletMessage("GIT CLONE FINISH");

        // init environments
        initSettings();

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

        CommonUtil.showJfigletMessage("GIT CLONE FINISH");
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
        Setting.getInstance().setPluginGitBranch(System.getenv(PLUGIN_GIT_BRANCH));
        Setting.getInstance().setPluginGitUrl(System.getenv(PLUGIN_GIT_URL));
        Setting.getInstance().setPluginGitWorkspace(System.getenv(PLUGIN_GIT_WORKSPACE));
        Setting.getInstance().setPluginToken(System.getenv(PLUGIN_TOKEN));
        Setting.getInstance().setPluginApi(System.getenv(PLUGIN_API));
    }

}
