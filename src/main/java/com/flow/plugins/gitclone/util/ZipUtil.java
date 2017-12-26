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

package com.flow.plugins.gitclone.util;

import com.flow.plugins.gitclone.App;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author yh@firim
 */
public class ZipUtil {

    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * readZipFile
     */
    public static String readZipFile(InputStream zippedStream) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(zippedStream)) {
            StringBuilder content = new StringBuilder();

            ZipEntry ze;
            byte[] buffer = new byte[2048];

            while ((ze = zis.getNextEntry()) != null) {

                int length = 0;
                while ((length = zis.read(buffer)) > 0) {
                    content.append(new String(buffer, 0, length, App.DEFAULT_CHARSET));
                }
            }

            return content.toString();
        }
    }

    /**
     * unzip zip file
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public static void unzip(Path zipFilePath, Path destDirectory) throws IOException {
        File destDir = new File(destDirectory.toString());
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath.toString()));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {

            String filePath = Paths.get(destDirectory.toString(), entry.getName()).toString();

            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
