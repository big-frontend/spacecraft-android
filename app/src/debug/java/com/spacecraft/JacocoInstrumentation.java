package com.spacecraft;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.electrolytej.main.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * adb shell am instrument  com.electrolytej.spacecraft/com.spacecraft.JacocoInstrumentation
 */
public class JacocoInstrumentation extends Instrumentation {
    public static String TAG = "JacocoInstrumentation:";
    private static String DEFAULT_COVERAGE_FILE_PATH = "/mnt/sdcard/coverage.ec";
    private final Bundle mResults = new Bundle();
    private Intent mIntent;

    public JacocoInstrumentation() {
    }

    @Override
    public void onCreate(Bundle arguments) {
        Log.d(TAG, "onCreate(" + arguments + ")");
        super.onCreate(arguments);
        DEFAULT_COVERAGE_FILE_PATH = getContext().getFilesDir().getPath().toString() + "/coverage.ec";
        File file = new File(DEFAULT_COVERAGE_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.d(TAG, "新建文件异常：" + e);
            }
        }
        mIntent = new Intent(getTargetContext(), MainActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void callActivityOnDestroy(Activity activity) {
        super.callActivityOnDestroy(activity);
        if (activity instanceof  MainActivity){
            Jacoco.generateCoverageReport();
//            finish(Activity.RESULT_OK, mResults);
        }
    }

}
