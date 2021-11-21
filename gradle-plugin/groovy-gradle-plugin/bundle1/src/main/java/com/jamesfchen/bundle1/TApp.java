package com.jamesfchen.bundle1;

import android.app.Application;

import com.jamesfchen.ibc.router.IBCRouter;
import com.jamesfchen.lifecycle.App;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * author jamesfchen
 * since 2017/7/4
 */
@App
public class TApp extends Application {
    private static TApp app;

    public static TApp getInstance() {
        if (app == null) {
            throw new IllegalStateException("app is null");
        }
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleObserver());
//        ProcessLifecycleOwner.get().getLifecycle().removeObserver();
//        RoutersManager.getInstance().register("bundle1",Bundle1Router.class);
//        try {
//            RoutersManager.getInstance().register("bundle1",Class.forName(" com.jamesfchen.bundle1.Bundle1Router",false,getClassLoader()));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            Log.d("cjf",Log.getStackTraceString(e));
//        }
        IBCRouter.init(this);



    }

}