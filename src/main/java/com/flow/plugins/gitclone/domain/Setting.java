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

package com.flow.plugins.gitclone.domain;

/**
 * @author yh@firim
 */
public class Setting {

    private String pluginApi;

    private String pluginToken;

    private String pluginGitUrl;

    private String pluginGitBranch;

    private String pluginGitWorkspace;

    private static Setting instance = new Setting();

    public static Setting getInstance() {
        return instance;
    }

    public String getPluginApi() {
        return pluginApi;
    }

    public void setPluginApi(String pluginApi) {
        this.pluginApi = pluginApi;
    }

    public String getPluginToken() {
        return pluginToken;
    }

    public void setPluginToken(String pluginToken) {
        this.pluginToken = pluginToken;
    }

    public String getPluginGitUrl() {
        return pluginGitUrl;
    }

    public void setPluginGitUrl(String pluginGitUrl) {
        this.pluginGitUrl = pluginGitUrl;
    }

    public String getPluginGitBranch() {
        return pluginGitBranch;
    }

    public void setPluginGitBranch(String pluginGitBranch) {
        this.pluginGitBranch = pluginGitBranch;
    }

    public String getPluginGitWorkspace() {
        return pluginGitWorkspace;
    }

    public void setPluginGitWorkspace(String pluginGitWorkspace) {
        this.pluginGitWorkspace = pluginGitWorkspace;
    }
}
