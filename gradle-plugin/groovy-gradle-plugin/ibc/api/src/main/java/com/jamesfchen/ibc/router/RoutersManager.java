package com.jamesfchen.ibc.router;

import android.content.Context;
import android.util.Log;

import androidx.collection.ArrayMap;

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author jamesfchen
 * @email hawksjamesf@gmail.com
 * @since 7月/08/2021  周四
 */
public class RoutersManager {
    private ArrayMap<String, IHybridRouter> hybridRouters;
    private ArrayMap<String, IFlutterRouter> flutterRouters;
    private ArrayMap<String, IReactNativeRouter> reactNativeRouters;
    private ArrayMap<String, INativeRouter> nativeRouters;
    private ArrayMap<String, Class<?>> registerRouters;
    private static final String ROUTER_CONFIG = "BundleManifest.xml";
    RoutersManager(){
        nativeRouters = new ArrayMap<>();
        registerRouters = new ArrayMap<>();
    }
    public static RoutersManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    ArrayMap<String, IHybridRouter> getHybridRouters(Context cxt) {
        if (hybridRouters == null) {
//            XmlParser.parse(cxt.assets.open(ROUTER_CONFIG))
        }
        return hybridRouters;
    }

    void getFlutterRouters() {
    }

    void getReactNativeRouters() {
    }

    void getNativeRouters() {
    }

    public void register(String routerName, Class<?> clz) {
        registerRouters.put(routerName, clz);
    }

    public INativeRouter find(String routerName) {
        INativeRouter iNativeRouter = nativeRouters.get(routerName);
        if (iNativeRouter ==null){
            Class<?> aClass = registerRouters.get(routerName);
            if (aClass ==null){
                throw new IllegalArgumentException(routerName +" 路由器没有注册");
            }
            try {
                iNativeRouter = (INativeRouter) aClass.newInstance();
                nativeRouters.put(routerName, (INativeRouter) iNativeRouter);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                Log.d("cjf",Log.getStackTraceString(e));
                return null;
            }
        }
        return iNativeRouter;
    }

    private static class LazyHolder {
        static final RoutersManager INSTANCE = new RoutersManager();

    }
}
