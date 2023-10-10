package com.jamesfchen.vi.util;

import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import android.os.Process;
public class CpuUtil {
    public static final String TAG = "CpuUtil";
    private static final int INVALID = 0;
    private static final String CPU_FILE_PATH_0 = "/sys/devices/system/cpu/";
    private static final String CPU_FILE_PATH_1 = "/sys/devices/system/cpu/possible";
    private static final String CPU_FILE_PATH_2 = "/sys/devices/system/cpu/present";

    public static double getMyAppCpuRate() {
        int appId = Process.myPid();
        return getAppCpuRate(appId);
    }

    public static double getAppCpuRate(int appId) {
        long cpuTime = 0L;
        long appTime = 0L;
        double cpuRate = 0.0D;
        try (RandomAccessFile procStatFile = new RandomAccessFile("/proc/stat", "r");) {
            String procStatString = procStatFile.readLine();
            String[] procStats = procStatString.split(" ");
            cpuTime = Long.parseLong(procStats[2]) + Long.parseLong(procStats[3]) + Long.parseLong(procStats[4]) + Long.parseLong(procStats[5]) + Long.parseLong(procStats[6]) + Long.parseLong(procStats[7]) + Long.parseLong(procStats[8]);

        } catch (Exception e) {
            Log.i(TAG, Log.getStackTraceString(e));
        }
        try (RandomAccessFile appStatFile = new RandomAccessFile("/proc/" + appId + "/stat", "r");) {
            String appStatString = appStatFile.readLine();
            String[] appStats = appStatString.split(" ");
            appTime = Long.parseLong(appStats[13]) + Long.parseLong(appStats[14]);
        } catch (Exception e) {
            Log.i(TAG, Log.getStackTraceString(e));
        }

        if (0 != cpuTime) {
            cpuRate = ((double) (appTime) / (double) (cpuTime)) * 100D;
        }

        return cpuRate;
    }

    public static int getNumOfCores() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            return 1;
        }
        int cores;
        try {
            cores = getCoresFromFile(CPU_FILE_PATH_1);
            if (cores == INVALID) {
                cores = getCoresFromFile(CPU_FILE_PATH_2);
            }
            if (cores == INVALID) {
                cores = getCoresFromCPUFiles(CPU_FILE_PATH_0);
            }
        } catch (Exception e) {
            cores = INVALID;
        }
        if (cores == INVALID) {
            cores = 1;
        }
        return cores;
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return Pattern.matches("cpu[0-9]", pathname.getName());
        }
    };

    private static int getCoresFromCPUFiles(String path) {
        File[] list = new File(path).listFiles(CPU_FILTER);
        return null == list ? 0 : list.length;
    }

    private static int getCoresFromFile(String file) {
        try (InputStream is = new FileInputStream(file)) {
            BufferedReader buf = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String fileContents = buf.readLine();
            buf.close();
            if (fileContents == null || !fileContents.matches("0-[\\d]+$")) {
                return INVALID;
            }
            String num = fileContents.substring(2);
            return Integer.parseInt(num) + 1;
        } catch (IOException e) {
            Log.i(TAG, Log.getStackTraceString(e));
            return INVALID;
        }
    }

    public static boolean is64BitRuntime() {
        final String currRuntimeABI = Build.CPU_ABI;
        return "arm64-v8a".equalsIgnoreCase(currRuntimeABI)
                || "x86_64".equalsIgnoreCase(currRuntimeABI)
                || "mips64".equalsIgnoreCase(currRuntimeABI);
    }

    public static String getAppCpuInfo() {
        String content = "cpu info:\n" + "app cpu rate:" + CpuUtil.getMyAppCpuRate() + ", cpu core:" + CpuUtil.getNumOfCores() +", " +(CpuUtil.is64BitRuntime() ? "64" : "32") + "bit runtime";
        return content;
    }
}
