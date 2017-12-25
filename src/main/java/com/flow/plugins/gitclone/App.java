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

/**
 * @author yh@fir.im
 */
public class App {

    private final static String PLUGIN_GIT_BRANCH = "PLUGIN_GIT_BRANCH";

    private final static String PLUGIN_GIT_WORKSPACE = "PLUGIN_GIT_WORKSPACE";

    private final static String PLUGIN_TOKEN = "PLUGIN_TOKEN";

    private final static String PLUGIN_API = "PLUGIN_API";

    private final static String PLUGIN_GIT_URL = "PLUGIN_GIT_URL";

    public static void main(String[] args) {

        // init environments
        initSettings();

        // download rsa zip

        // git clone

    }

    private static void initSettings() {
        Setting.getInstance().setPluginGitBranch(System.getenv(PLUGIN_GIT_BRANCH));
        Setting.getInstance().setPluginGitUrl(System.getenv(PLUGIN_GIT_URL));
        Setting.getInstance().setPluginGitWorkspace(System.getenv(PLUGIN_GIT_WORKSPACE));
        Setting.getInstance().setPluginToken(System.getenv(PLUGIN_TOKEN));
        Setting.getInstance().setPluginApi(System.getenv(PLUGIN_API));
    }
}
