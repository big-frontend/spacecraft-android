package com.dianping.v1;

import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Nov/27/2020  Fri
 */
public class PackageManagerProxy implements InvocationHandler {
    private IPackageManager mPackageManager;
    public byte[] qqSign = new byte[]{(byte) 48, (byte) -126, (byte) 2, (byte) 95, (byte) 48, (byte) -126, (byte) 1, (byte) -56, (byte) -96, (byte) 3, (byte) 2, (byte) 1, (byte) 2, (byte) 2, (byte) 4, (byte) 74, (byte) -124, (byte) -63, (byte) -43, (byte) 48, (byte) 13, (byte) 6, (byte) 9, (byte) 42, (byte) -122, (byte) 72, (byte) -122, (byte) -9, (byte) 13, (byte) 1, (byte) 1, (byte) 5, (byte) 5, (byte) 0, (byte) 48, (byte) 116, (byte) 49, (byte) 11, (byte) 48, (byte) 9, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 6, (byte) 19, (byte) 2, (byte) 67, (byte) 78, (byte) 49, (byte) 17, (byte) 48, (byte) 15, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 8, (byte) 19, (byte) 8, (byte) 115, (byte) 104, (byte) 97, (byte) 110, (byte) 103, (byte) 104, (byte) 97, (byte) 105, (byte) 49, (byte) 17, (byte) 48, (byte) 15, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 7, (byte) 19, (byte) 8, (byte) 115, (byte) 104, (byte) 97, (byte) 110, (byte) 103, (byte) 104, (byte) 97, (byte) 105, (byte) 49, (byte) 21, (byte) 48, (byte) 19, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 10, (byte) 19, (byte) 12, (byte) 100, (byte) 105, (byte) 97, (byte) 110, (byte) 112, (byte) 105, (byte) 110, (byte) 103, (byte) 46, (byte) 99, (byte) 111, (byte) 109, (byte) 49, (byte) 21, (byte) 48, (byte) 19, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 11, (byte) 19, (byte) 12, (byte) 100, (byte) 105, (byte) 97, (byte) 110, (byte) 112, (byte) 105, (byte) 110, (byte) 103, (byte) 46, (byte) 99, (byte) 111, (byte) 109, (byte) 49, (byte) 17, (byte) 48, (byte) 15, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 3, (byte) 19, (byte) 8, (byte) 84, (byte) 117, (byte) 32, (byte) 89, (byte) 105, (byte) 109, (byte) 105, (byte) 110, (byte) 48, (byte) 30, (byte) 23, (byte) 13, (byte) 48, (byte) 57, (byte) 48, (byte) 56, (byte) 49, (byte) 52, (byte) 48, (byte) 49, (byte) 52, (byte) 53, (byte) 53, (byte) 55, (byte) 90, (byte) 23, (byte) 13, (byte) 51, (byte) 54, (byte) 49, (byte) 50, (byte) 51, (byte) 48, (byte) 48, (byte) 49, (byte) 52, (byte) 53, (byte) 53, (byte) 55, (byte) 90, (byte) 48, (byte) 116, (byte) 49, (byte) 11, (byte) 48, (byte) 9, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 6, (byte) 19, (byte) 2, (byte) 67, (byte) 78, (byte) 49, (byte) 17, (byte) 48, (byte) 15, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 8, (byte) 19, (byte) 8, (byte) 115, (byte) 104, (byte) 97, (byte) 110, (byte) 103, (byte) 104, (byte) 97, (byte) 105, (byte) 49, (byte) 17, (byte) 48, (byte) 15, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 7, (byte) 19, (byte) 8, (byte) 115, (byte) 104, (byte) 97, (byte) 110, (byte) 103, (byte) 104, (byte) 97, (byte) 105, (byte) 49, (byte) 21, (byte) 48, (byte) 19, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 10, (byte) 19, (byte) 12, (byte) 100, (byte) 105, (byte) 97, (byte) 110, (byte) 112, (byte) 105, (byte) 110, (byte) 103, (byte) 46, (byte) 99, (byte) 111, (byte) 109, (byte) 49, (byte) 21, (byte) 48, (byte) 19, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 11, (byte) 19, (byte) 12, (byte) 100, (byte) 105, (byte) 97, (byte) 110, (byte) 112, (byte) 105, (byte) 110, (byte) 103, (byte) 46, (byte) 99, (byte) 111, (byte) 109, (byte) 49, (byte) 17, (byte) 48, (byte) 15, (byte) 6, (byte) 3, (byte) 85, (byte) 4, (byte) 3, (byte) 19, (byte) 8, (byte) 84, (byte) 117, (byte) 32, (byte) 89, (byte) 105, (byte) 109, (byte) 105, (byte) 110, (byte) 48, (byte) -127, (byte) -97, (byte) 48, (byte) 13, (byte) 6, (byte) 9, (byte) 42, (byte) -122, (byte) 72, (byte) -122, (byte) -9, (byte) 13, (byte) 1, (byte) 1, (byte) 1, (byte) 5, (byte) 0, (byte) 3, (byte) -127, (byte) -115, (byte) 0, (byte) 48, (byte) -127, (byte) -119, (byte) 2, (byte) -127, (byte) -127, (byte) 0, (byte) -53, (byte) -100, (byte) -70, (byte) -1, (byte) -83, (byte) -118, (byte) 15, (byte) -49, (byte) -1, (byte) 66, (byte) 25, (byte) -10, (byte) 127, (byte) 100, (byte) -89, (byte) -47, (byte) 3, (byte) -19, (byte) 44, (byte) -47, (byte) 61, (byte) -71, (byte) 102, (byte) -59, (byte) -74, (byte) 58, (byte) 100, (byte) 58, (byte) 46, (byte) 52, (byte) 122, (byte) 84, (byte) 60, (byte) -116, (byte) -78, (byte) 125, (byte) -45, (byte) 43, (byte) -126, (byte) -67, (byte) -27, (byte) -44, (byte) 38, (byte) -38, (byte) -37, (byte) -14, (byte) -97, (byte) -114, (byte) 126, (byte) 120, (byte) -29, (byte) 101, (byte) 75, (byte) -34, (byte) -19, (byte) 78, (byte) -44, (byte) 83, (byte) -108, (byte) 97, (byte) -89, (byte) 50, (byte) -81, (byte) -16, (byte) -109, (byte) 87, (byte) -121, (byte) -37, (byte) -7, (byte) -120, (byte) -126, (byte) -6, (byte) -92, (byte) -10, (byte) 112, (byte) -12, (byte) -31, (byte) 109, (byte) -12, (byte) 19, (byte) -126, (byte) -55, (byte) -69, (byte) 17, (byte) -54, (byte) 101, (byte) -104, (byte) 36, (byte) 100, (byte) -109, (byte) -55, (byte) 118, (byte) -122, (byte) -55, (byte) -52, (byte) -127, (byte) -46, (byte) 0, (byte) 127, (byte) -60, (byte) 69, (byte) -117, (byte) -35, (byte) -53, (byte) -104, (byte) -114, (byte) -91, (byte) 30, (byte) 108, (byte) -87, (byte) 58, (byte) 56, (byte) 101, (byte) -119, (byte) 59, (byte) -95, (byte) -42, (byte) -32, (byte) 17, (byte) 33, (byte) -59, (byte) -33, (byte) -92, (byte) -117, (byte) -40, (byte) 33, (byte) -92, (byte) -77, (byte) 2, (byte) 3, (byte) 1, (byte) 0, (byte) 1, (byte) 48, (byte) 13, (byte) 6, (byte) 9, (byte) 42, (byte) -122, (byte) 72, (byte) -122, (byte) -9, (byte) 13, (byte) 1, (byte) 1, (byte) 5, (byte) 5, (byte) 0, (byte) 3, (byte) -127, (byte) -127, (byte) 0, (byte) 10, (byte) 11, (byte) -119, (byte) -63, (byte) -86, (byte) 112, (byte) -52, (byte) 12, (byte) 72, (byte) 124, (byte) -36, (byte) -51, (byte) 42, (byte) -81, (byte) 29, (byte) -82, (byte) 126, (byte) 74, (byte) 98, (byte) 127, (byte) 112, (byte) -97, (byte) -111, (byte) 8, (byte) -103, (byte) 28, (byte) -43, (byte) -93, (byte) 99, (byte) -123, (byte) -41, (byte) -29, (byte) 72, (byte) -4, (byte) -83, (byte) 54, (byte) 90, (byte) -13, (byte) -5, (byte) -59, (byte) 21, (byte) 72, (byte) 48, (byte) -128, (byte) 63, (byte) 1, (byte) -70, (byte) 82, (byte) 32, (byte) 106, (byte) 112, (byte) -123, (byte) 114, (byte) 72, (byte) -58, (byte) 117, (byte) 9, (byte) 87, (byte) 95, (byte) 113, (byte) -51, (byte) -51, (byte) -50, (byte) 84, (byte) 71, (byte) 28, (byte) 34, (byte) 90, (byte) -77, (byte) -76, (byte) -107, (byte) 125, (byte) 48, (byte) -100, (byte) 100, (byte) -2, (byte) -110, (byte) 8, (byte) -64, (byte) 32, (byte) -94, (byte) 57, (byte) -99, (byte) -47, (byte) -89, (byte) -30, (byte) -83, (byte) 45, (byte) 49, (byte) -85, (byte) 60, (byte) -49, (byte) 54, (byte) -53, (byte) -72, (byte) -62, (byte) -101, (byte) -65, (byte) 112, (byte) -125, (byte) -48, (byte) -26, (byte) -107, (byte) 59, (byte) -20, (byte) 71, (byte) -115, (byte) 117, (byte) -9, (byte) -123, (byte) -27, (byte) -13, (byte) 40, (byte) 110, (byte) -84, (byte) 99, (byte) 122, (byte) -66, (byte) -29, (byte) -57, (byte) -104, (byte) 60, (byte) -73, (byte) 123, (byte) 35, (byte) -36, (byte) 60, (byte) -94};

    public PackageManagerProxy(IPackageManager packageManager) {
        this.mPackageManager = packageManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d("hook", "method name:" + method.getName());
        if ("getPackageInfo".equals(method.getName())) {
//            if (args.length == 3 && ((String)args[0]).contains("dianping")
            if (args.length == 3 && "com.hawksjamesf.spacecraft.debug".equals(args[0])
                    && ((Integer) args[1] == 64 || (Integer) args[1] == 0x08000000)) {
                Log.d("cjf", args[0] + "  " + (Integer) args[1] + " " + args[2]);
                PackageInfo info = (PackageInfo) method.invoke(mPackageManager, args);
                if (info == null) return null;
                Log.d("cjf", "hook " + method.getName() + " before:" + info.firstInstallTime + " " + info.lastUpdateTime + " \n" + sha1ToHexString(info.signatures[0].toByteArray()));
//                info.firstInstallTime = 1;
//                info.lastUpdateTime = 11;
                info.signatures[0] = new Signature(qqSign);
                Log.d("cjf", "hook " + method.getName() + " after:" + info.firstInstallTime + " " + info.lastUpdateTime + " \n" + sha1ToHexString(info.signatures[0].toByteArray()));
                return info;
            } else {
                Log.d("hook", "getPackageInfo方法形参数量:" + args.length);
            }
        }
        return method.invoke(mPackageManager, args);
    }

    public static String sha1ToHexString(byte[] cert) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i]).toUpperCase(Locale.US);
                if (appendString.length() == 1) hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

