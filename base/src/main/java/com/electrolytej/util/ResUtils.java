package com.electrolytej.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResUtils {


    public static final String RAW_PATH = "android.resource://%s/%s";
    public static final String ASSETS_PATH = "file:///android_asset/%s";

    public static String getRawPath(String packageName, @RawRes int resid) {
        return String.format(RAW_PATH, packageName, resid);
    }

    public static String getAssetsPath(String fileName) {
        return String.format(ASSETS_PATH, fileName);
    }

    @Nullable
    public static String loadAssetFile(Context context, String fileName) {
        StringBuilder sb = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));) {
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException ignored) {
            return null;
        }
    }

    @Nullable
    public static String loadRawFile(final Context context, @RawRes final int resourceId) {
        final StringBuilder sb = new StringBuilder();
        Resources resources = context.getResources();
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resources.openRawResource(resourceId)))) {
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                sb.append(nextLine);
//                sb.append('\n');
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }

    }
}
