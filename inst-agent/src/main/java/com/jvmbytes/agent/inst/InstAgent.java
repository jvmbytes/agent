/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jvmbytes.agent.inst;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.jar.JarFile;

/**
 * @author wongoo
 */
public final class InstAgent {

    private static final String EXPORTER_JAR = "/jvmbytes-inst-exporter.jar";

    private static final String EXPORTER_CLASS = "java.com.jvmbytes.agent.inst.InstExporter";

    private static void exportInst(String feature, Instrumentation inst) {
        try {
            inst.appendToBootstrapClassLoaderSearch(new JarFile(loadExporterJar()));
        } catch (Exception e) {
            throw new RuntimeException("can't export Instrumentation", e);
        }

        try {
            Class<?> clazz = InstAgent.class.getClassLoader().loadClass(EXPORTER_CLASS);
            if (clazz == null) {
                throw new Exception("can't find class " + EXPORTER_CLASS);
            }
            Method method = clazz.getMethod("storeFeature", String.class);
            method.invoke(null, feature);

            method = clazz.getMethod("storeInst", Instrumentation.class);
            method.invoke(null, inst);
        } catch (Exception e) {
            throw new RuntimeException("can't store Instrumentation", e);
        }

        System.out.println("jvmbytes inst agent loaded");
    }

    private static File loadExporterJar() throws Exception {
        File tempJar = File.createTempFile("jvmbytes-inst-exporter", ".jar");
        String exporterJarPath = tempJar.getParent() + EXPORTER_JAR;
        File exporterJar = new File(exporterJarPath);
        try {
            if (exporterJar.exists()) {
                tempJar.delete();
            } else {
                InputStream is = InstAgent.class.getResourceAsStream(EXPORTER_JAR);
                if (is == null) {
                    throw new RuntimeException("can't find " + EXPORTER_JAR);
                }
                FileOutputStream os = new FileOutputStream(tempJar);
                byte[] bytes = new byte[1024];
                int n = 0;
                while ((n = is.read(bytes)) > 0) {
                    os.write(bytes, 0, n);
                }
                os.close();
                is.close();

                tempJar.renameTo(exporterJar);
            }
        } catch (Exception e) {
            try {
                // make sure temp jar being deleted
                tempJar.delete();
            } catch (Exception ex) {
            }

            // check again
            if (exporterJar.exists()) {
                return exporterJar;
            }

            throw e;
        }

        return exporterJar;
    }

    public static void premain(String feature, Instrumentation inst) {
        exportInst(feature, inst);
    }

    public static void agentmain(String feature, Instrumentation inst) {
        exportInst(feature, inst);
    }

}
