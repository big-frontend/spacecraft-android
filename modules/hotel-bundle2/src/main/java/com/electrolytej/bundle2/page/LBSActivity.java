package com.electrolytej.bundle2.page;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.PhoneUtils;

public class LBSActivity extends AppCompatActivity {
    public static final String TAG = "LBS_collection";
    protected String auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String seed = createAuthoritySeed();
        auth = "Basic " + Base64.encodeToString(seed.getBytes(), Base64.NO_WRAP);
    }

    private String createAuthoritySeed() {
        String realImei;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            realImei = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            // Caused by: java.lang.SecurityException: getUniqueDeviceId: The user 10313 does not meet the requirements to access device identifiers.
            realImei = PhoneUtils.getIMEI();
        }
        return "_android." + realImei + ":" + "HUAWEI" + "-" + "NEXUS" + "-" + realImei;
    }
}
