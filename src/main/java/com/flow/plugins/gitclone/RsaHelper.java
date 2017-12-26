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
import com.flow.platform.util.http.HttpClient;
import com.flow.platform.util.http.HttpResponse;
import com.flow.plugins.gitclone.domain.Setting;
import com.flow.plugins.gitclone.exception.PluginException;
import com.flow.plugins.gitclone.util.ZipUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipInputStream;

/**
 * @author yh@fir.im
 */
public class RsaHelper {

    private final static String RSA_FOLDER = "rsa";

    private final static String RSA_ZIP = "rsa.zip";

    private final static String RSA_PRIVATE_NAME = "RSA";

    private final static String RSA_PUBLIC_NAME = "RSA";

    private final static Logger LOGGER = new Logger(RsaHelper.class);

    public RsaHelper() {
    }

    public void downloadRsaAndUnzip(String url, Path destPath) {
        downloadRsaZip(url);
        unzipRsa(destPath);
    }

    public Path privateKeyPath(Path destPath) {
        Path path = Paths.get(destPath.toString(), RSA_FOLDER, RSA_PRIVATE_NAME);
        return path;
    }

    public Path publicKeyPath(Path destPath) {
        Path path = Paths.get(destPath.toString(), RSA_FOLDER, RSA_PUBLIC_NAME);
        return path;
    }

    private void downloadRsaZip(String url) {
        HttpClient.build(url).bodyAsStream((HttpResponse<InputStream> item) -> {
            try {
                ZipInputStream zipInputStream = new ZipInputStream(item.getBody());

                String content = ZipUtil.readZipFile(zipInputStream);

                Path path = Paths.get(Setting.getInstance().getPluginGitWorkspace(), RSA_ZIP);
                Files.createDirectories(path);

                Files.write(path, content.getBytes(App.DEFAULT_CHARSET));

            } catch (IOException e) {
                throw new PluginException("Download Rsa error " + e.getMessage());
            }
        });
    }

    private void unzipRsa(Path destPath) {

        try {
            Path path = Paths.get(destPath.toString(), RSA_FOLDER);
            if (path.toFile().exists()) {
                ZipUtil.unzip(path, path.getParent());
            } else {
                LOGGER.traceMarker("UnzipRsa", "Not found zip file continue");
            }
        } catch (IOException e) {
            LOGGER.traceMarker("UnzipRsa", "Unzip File happens some error " + e.getMessage());
        }

    }

}
