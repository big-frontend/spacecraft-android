package com.spacecraft;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class Jacoco {
    public static boolean generateCoverageReport() {
        // use reflection to call emma dump coverage method, to avoid
        // always statically compiling against emma jar
        String coverageFilePath = "/sdcard/coverage.exec";
        java.io.File coverageFile = new java.io.File(coverageFilePath);
        try {
            Class<?> emmaRTClass = Class.forName("com.vladium.emma.rt.RT");
            Method dumpCoverageMethod = emmaRTClass.getMethod("dumpCoverageData", coverageFile.getClass(), boolean.class, boolean.class);
            dumpCoverageMethod.invoke(null, coverageFile, false, false);
            return true;
        } catch (Exception  e) {
            String DEFAULT_COVERAGE_FILE_PATH = "/mnt/sdcard/coverage.ec";
//            new Throwable("Is emma jar on classpath?", e);
            try (OutputStream out = new FileOutputStream(DEFAULT_COVERAGE_FILE_PATH, false)) {
                Object agent = Class.forName("org.jacoco.agent.rt.RT").getMethod("getAgent").invoke(null);
                out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class).invoke(agent, false));
                return true;
            } catch (Exception exception) {
                return false;
            }
        }
    }
}
