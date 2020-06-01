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
