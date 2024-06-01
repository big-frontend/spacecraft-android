package com.electrolytej;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 */
public class EventReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //接收安装广播
        Log.e("cjf","有了"+intent.getAction());
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            Log.e("cjf","安装了:" + packageName + "包名的程序");
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            Log.e("cjf","卸载了:" + packageName + "包名的程序");
        }
//        String action = intent.getAction();
//        String localPkgName = context.getPackageName();//取得MyReceiver所在的App的包名
//        Uri data = intent.getData();
//        String installedPkgName = data.getSchemeSpecificPart();//取得安装的Apk的包名，只在该app覆盖安装后自启动
//        if((action.equals(Intent.ACTION_PACKAGE_ADDED)
//                || action.equals(Intent.ACTION_PACKAGE_REPLACED)) && installedPkgName.equals(localPkgName)){
//            Intent launchIntent = new Intent(context, YPosedActivity.class);
//            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(launchIntent);
//        }
    }
}
