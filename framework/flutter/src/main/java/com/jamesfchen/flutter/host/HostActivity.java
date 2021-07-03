package com.jamesfchen.flutter.host;

import android.os.Bundle;

import com.jamesfchen.flutter.plugin.GeneratedPluginRegistrant;

import io.flutter.embedding.android.FlutterActivity;

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: hawksjamesf@gmail.com
 * @since: 五月/20/2021  星期四
 */
public class HostActivity  extends FlutterActivity {

    private static final String CHANNEL = "com.startActivity/testChannel";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(getFlutterEngine());
        /**
         * static const platform = const MethodChannel(CHANNEL);
         * string result await platform.invokeMethod("Bundle2Activity");
         */
//        new MethodChannel(getFlutterView(),CHANNEL).setMethodCallHandler((call, result) -> {
                //     flutter://flutter-bundle/main?from=afdasf
                //     URI.create(call.method)

//            if(call.method.equals("Bundle2Activity")){
//                Intent intent=new Intent(this,Bundle2Activity.class);
//                startActivity(intent);
//                result.success("ActivityStarted");
//            }
//            else{
//                result.notImplemented();
//            }
//        });
    }
}
