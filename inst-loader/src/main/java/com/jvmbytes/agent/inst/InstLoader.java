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
