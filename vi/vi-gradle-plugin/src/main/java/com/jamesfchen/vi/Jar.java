package com.jamesfchen.vi;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Jar {
    private static final Map<String, File> mExtracted = new HashMap<>();

    public static File getResourceAsFile(String name, Class<?> clazz) throws JarException {
        File file = mExtracted.get(name);
        if (file == null) {
            file = extractToTmp(name, clazz);
            mExtracted.put(name, file);
        }
        return file;
    }

    public static File extractToTmp(String resourcePath, Class<?> clazz) throws JarException {
        return extractToTmp(resourcePath, "brut_util_Jar_", clazz);
    }

    public static File extractToTmp(String resourcePath, String tmpPrefix, Class<?> clazz) throws JarException {
        try {
            InputStream in = clazz.getResourceAsStream(resourcePath);
            if (in == null) {
                throw new FileNotFoundException(resourcePath);
            }
            long suffix = ThreadLocalRandom.current().nextLong();
            suffix = suffix == Long.MIN_VALUE ? 0 : Math.abs(suffix);
            File fileOut = File.createTempFile(tmpPrefix, suffix + ".tmp");
            fileOut.deleteOnExit();

            OutputStream out = new FileOutputStream(fileOut);
            IOUtils.copy(in, out);
            in.close();
            out.close();

            return fileOut;
        } catch (IOException ex) {
            throw new JarException("Could not extract resource: " + resourcePath, ex);
        }
    }
}
