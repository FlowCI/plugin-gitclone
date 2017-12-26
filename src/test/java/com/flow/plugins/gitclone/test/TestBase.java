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

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.common.io.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.Charsets;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runners.MethodSorters;

/**
 * @author yh@firim
 */
@FixMethodOrder(MethodSorters.JVM)
public class TestBase {

    @Rule
    public WireMockRule wiremock = new WireMockRule(8080);

    {
        try {
            stubDemo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void stubDemo() throws IOException {
        wiremock.resetAll();
        wiremock.stubFor(
            get(urlEqualTo("/credentials/rsa/download"))
                .willReturn(aResponse().withStatus(200).withBody(getResource("2048.zip"))));
    }

    protected String getResource(String fileName) throws IOException {
        Path path = Paths.get(GitHelperTest.class.getClassLoader().getResource(fileName).getPath());
        return Files.toString(path.toFile(), Charsets.UTF_8);
    }

    protected Path getPath(String fileName) {
        return Paths.get(GitHelperTest.class.getClassLoader().getResource(fileName).getPath());
    }
}
