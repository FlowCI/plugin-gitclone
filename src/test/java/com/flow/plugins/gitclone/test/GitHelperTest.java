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

import com.flow.plugins.gitclone.GitHelper;
import com.flow.plugins.gitclone.exception.PluginException;
import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author yh@fir.im
 */
public class GitHelperTest extends TestBase {

    private static final String GIT_URL = "git@github.com:yunheli/plugins-1.git";

    private static final String ERROR_GIT_URL = "git@github.com:yunheli/plugins-1";

    private static final String BRANCH = "patch-1";

    private static final String RSA_PRIVATE_NAME = "id_rsa";

    private GitHelper gitHelper = new GitHelper();

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();


    @Test
    public void should_git_clone_success() throws IOException {
        File tmpFolder = tmp.newFolder();
        gitHelper.fetchCode(GIT_URL, getPath(RSA_PRIVATE_NAME), BRANCH, tmpFolder.toPath());

        Assert.assertEquals(1, tmpFolder.list().length);
    }


    @Test(expected = PluginException.class)
    public void should_detect_git_success() throws IOException {

        File tmpFolder = tmp.newFolder();
        gitHelper.fetchCode(ERROR_GIT_URL, getPath(RSA_PRIVATE_NAME), BRANCH, tmpFolder.toPath());

    }

}
