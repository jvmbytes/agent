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

package java.com.jvmbytes.agent.inst;

import java.lang.instrument.Instrumentation;

/**
 * @author wongoo
 */
public final class InstExporter {

    private InstExporter() {
    }

    private static InstExporter exporter = new InstExporter();

    public static InstExporter instance() {
        return exporter;
    }

    private static String feature;

    private static Instrumentation inst;

    public static String exportFeature() {
        return feature;
    }

    public static Instrumentation exportInst() {
        return inst;
    }

    public static void storeFeature(String feature) {
        InstExporter.feature = feature;
    }

    public static void storeInst(Instrumentation inst) {
        InstExporter.inst = inst;
    }
}
