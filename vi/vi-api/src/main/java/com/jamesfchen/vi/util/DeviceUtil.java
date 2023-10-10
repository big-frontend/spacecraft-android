package com.jamesfchen.vi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DeviceUtil {
    private static LEVEL sLevelCache = null;
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            if (null != reader) {
                reader.close();
            }
        }

        return sb.toString();
    }

    public static String getStringFromFile(String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = null;
        String ret;
        try {
            fin = new FileInputStream(fl);
            ret = convertStreamToString(fin);
        } finally {
            if (null != fin) {
                fin.close();
            }
        }
        return ret;
    }
    public static LEVEL getLevel() {
        if (null != sLevelCache) {
            return sLevelCache;
        }
        MemoryUtil.RamInfo ramInfo = MemoryUtil.getRamInfo();
        long totalMemory = ramInfo.getTotalMemKb() * 1024;
        int coresNum = CpuUtil.getNumOfCores();
        if (totalMemory >= 8L * MemoryUnit.GB) {
            sLevelCache = LEVEL.BEST;
        } else if (totalMemory >= 6L * MemoryUnit.GB) {
            sLevelCache = LEVEL.HIGH;
        } else if (totalMemory >= 4L * MemoryUnit.GB) {
            sLevelCache = LEVEL.MIDDLE;
        } else if (totalMemory >= 2L * MemoryUnit.GB) {
            if (coresNum >= 4) {
                sLevelCache = LEVEL.MIDDLE;
            } else if (coresNum > 0) {
                sLevelCache = LEVEL.LOW;
            }
        } else if (totalMemory >= 0) {
            sLevelCache = LEVEL.BAD;
        } else {
            sLevelCache = LEVEL.UN_KNOW;
        }
        return sLevelCache;
    }
    public enum LEVEL {

        BEST(5), HIGH(4), MIDDLE(3), LOW(2), BAD(1), UN_KNOW(-1);

        int value;

        LEVEL(int val) {
            this.value = val;
        }

        public int getValue() {
            return value;
        }
    }
}
