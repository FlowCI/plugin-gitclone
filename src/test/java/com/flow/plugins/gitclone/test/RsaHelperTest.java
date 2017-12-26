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

package com.flow.plugins.gitclone.test;

import com.flow.plugins.gitclone.RsaHelper;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author yh@fir.im
 */
public class RsaHelperTest extends TestBase{

    private final RsaHelper rsaHelper = new RsaHelper();

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void should_download_unzip_rsa_success() throws IOException {
        Path tmp = temp.newFolder().toPath();
        Assert.assertEquals(0, tmp.toFile().list().length);

//        String downloadUrl = "http://localhost:8088/credentials/2048/download";
//        rsaHelper.downloadRsaAndUnzip(downloadUrl, tmp);
//
//        Assert.assertEquals(true,  rsaHelper.privateKeyPath(tmp).toFile().exists());
//        Assert.assertEquals(true,  rsaHelper.publicKeyPath(tmp).toFile().exists());
    }
}
