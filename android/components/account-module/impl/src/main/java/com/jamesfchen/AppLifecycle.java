package com.jamesfchen;

import com.jamesfchen.login.signin.SigInModule;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

@com.jamesfchen.lifecycle.AppLifecycle
public class AppLifecycle implements DefaultLifecycleObserver {
    private static AppComponent sAppComponent;
    private static NetComponent sNetComponent;

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        sNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();
        sAppComponent = DaggerAppComponent.builder()
//                .netComponent(sNetComponent)
                .appModule(new AppModule())
                .sigInModule(new SigInModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static NetComponent getNetComponet() {
        return sNetComponent;
    }
}