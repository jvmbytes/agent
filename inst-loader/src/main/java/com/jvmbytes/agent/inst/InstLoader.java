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

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

/**
 * @author wongoo
 */
public final class InstLoader {

    private static final String EXPORTER_CLASS = "java.com.jvmbytes.agent.inst.InstExporter";

    private InstLoader() {
    }

    private static InstLoader instance = new InstLoader();

    private static InstLoader instance() {
        return instance;
    }

    private static String feature;
    private static Instrumentation inst;

    public static Instrumentation loadInst() {
        if (inst == null) {
            loadExporter();
        }
        return inst;
    }

    public static String loadFeature() {
        if (inst == null) {
            loadExporter();
        }
        return feature;
    }

    private synchronized static void loadExporter() {
        if (inst != null) {
            return;
        }

        try {
            Class<?> clazz = InstLoader.class.getClassLoader().loadClass(EXPORTER_CLASS);
            if (clazz == null) {
                throw new Exception("can't find class " + EXPORTER_CLASS);
            }
            Method method = clazz.getMethod("exportInst");
            inst = (Instrumentation) method.invoke(null);
            if (inst == null) {
                throw new Exception("can't get export Instrumentation");
            }
            method = clazz.getMethod("exportFeature");
            feature = (String) method.invoke(null);
        } catch (Exception e) {
            throw new RuntimeException("can't load exporter, " + e.getClass().getName() + ":" + e.getMessage());
        }
    }
}
