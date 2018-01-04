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

import com.flow.plugins.gitclone.util.GitUtil;
import java.nio.file.Path;
import org.eclipse.jgit.lib.ProgressMonitor;

/**
 * @author yh@fir.im
 */
public class GitHelper {

    private final static Logger LOGGER = LoggerFactory.build(GitHelper.class);

    public void fetchCode(String gitUrl, Path privateKeyPath, String branch, Path targetFolder) {
        GitUtil.gitClone(gitUrl, privateKeyPath, branch, targetFolder, new CloneMonitor());
        LOGGER.trace("Finish Fetch Code");
    }

    public void fetchCode(String gitUrl, String user, String pass, String branch, Path targetFolder) {
        GitUtil.gitClone(gitUrl, user, pass, branch, targetFolder, new CloneMonitor());
        LOGGER.trace("Finish Fetch Code");
    }

    private class CloneMonitor implements ProgressMonitor {

        @Override
        public void start(int i) {
            LOGGER.trace("Start Fetch Code");
        }

        @Override
        public void beginTask(String s, int i) {
            LOGGER.trace(s);
        }

        @Override
        public void update(int i) {
        }

        @Override
        public void endTask() {
        }

        @Override
        public boolean isCancelled() {
            return false;
        }
    }
}
