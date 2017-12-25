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

import com.flow.platform.util.git.GitClient;
import com.flow.platform.util.git.GitSshClient;
import com.flow.platform.util.git.JGitBasedClient;
import com.flow.platform.util.http.HttpClient;
import com.flow.platform.util.http.HttpResponse;
import com.flow.plugins.gitclone.domain.Setting;
import com.flow.plugins.gitclone.exception.PluginException;
import com.flow.plugins.gitclone.util.ZipUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipInputStream;

/**
 * @author yh@fir.im
 */
public class App {

    public final static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private final static String PLUGIN_GIT_BRANCH = "PLUGIN_GIT_BRANCH";

    private final static String PLUGIN_GIT_WORKSPACE = "PLUGIN_GIT_WORKSPACE";

    private final static String PLUGIN_TOKEN = "PLUGIN_TOKEN";

    private final static String PLUGIN_API = "PLUGIN_API";

    private final static String PLUGIN_GIT_URL = "PLUGIN_GIT_URL";

    private final static String RSA_FOLDER = "rsa";

    private final static String RSA_ZIP = "rsa.zip";

    public static void main(String[] args) {

        // init environments
        initSettings();

        // download rsa zip
        downloadRsa(Setting.getInstance().getPluginApi());

        // git clone


    }

    private static void initSettings() {
        Setting.getInstance().setPluginGitBranch(System.getenv(PLUGIN_GIT_BRANCH));
        Setting.getInstance().setPluginGitUrl(System.getenv(PLUGIN_GIT_URL));
        Setting.getInstance().setPluginGitWorkspace(System.getenv(PLUGIN_GIT_WORKSPACE));
        Setting.getInstance().setPluginToken(System.getenv(PLUGIN_TOKEN));
        Setting.getInstance().setPluginApi(System.getenv(PLUGIN_API));
    }

    private static void downloadRsa(String url) {

        HttpClient.build(url).bodyAsStream((HttpResponse<InputStream> item) -> {
            try {
                ZipInputStream zipInputStream = new ZipInputStream(item.getBody());

                String content = ZipUtil.readZipFile(zipInputStream);

                Path path = Paths.get(Setting.getInstance().getPluginGitWorkspace(), RSA_FOLDER);
                Files.createDirectories(path);

                Files.write(path, content.getBytes(DEFAULT_CHARSET));

            } catch (IOException e) {
                throw new PluginException("Download Rsa error " + e.getMessage());
            }
        });
    }


}
