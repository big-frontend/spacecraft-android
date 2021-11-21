package com.jamesfchen.ibc.router;

import android.content.Context;
import android.util.Log;

import java.net.URI;
import java.util.Arrays;

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author jamesfchen
 * @email hawksjamesf@gmail.com
 * @since 7月/08/2021  周四
 * <p>
 * *
 * *
 * * scheme://router-name/page?param1=value1&param2=value2 ...
 * *
 * * scheme:
 * * - http/hybrid
 * * - reactnative
 * * - flutter
 * * - native
 */
public class IBCRouter {
    public static void init(Context cxt) {
        //通过编译期插桩注册路由器
//            RoutersManager.getInstance().register(Bundle1Router::class.java)
//            RoutersManager.getInstance().register(Bundle2Router::class.java)
//            RoutersManager.getInstance().register("bundle1",Class.forName(" com.jamesfchen.bundle1.Bundle1Router"))
//            RoutersManager.getInstance().register("bundle2",Class.forName(" com.jamesfchen.bundle2.Bundle1Router"))
    }

    public static void goNativeBundle(Context cxt, String path) {
//        go(cxt, URI.create("native://"+path));
        String[] split = path.split("/");
        Log.d("cjf", "split "+Arrays.toString(split));
        INativeRouter router = RoutersManager.getInstance().find(split[0]);
        router.go(cxt, split[1], null);
    }

    public static void go(Context cxt, URI uri) {
        Log.d("cjf", uri+" "+uri.getScheme()+" "+uri.getHost()+" "+uri.getPath()+" "+uri.getQuery());
        if (uri.getScheme().equals("http")) {
            //如果http内部调整不了就发送到外部应用，比如浏览器就可以处理

        } else if (uri.getScheme().equals("native")) {
            String[] split = uri.getPath().split("/");
            INativeRouter router = RoutersManager.getInstance().find(split[0]);
            router.go(cxt, split[1], null);
        }
    }
}
