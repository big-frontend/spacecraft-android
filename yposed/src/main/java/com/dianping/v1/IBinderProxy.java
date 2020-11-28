package com.dianping.v1;

import android.content.pm.IPackageManager;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/28/2020  Sat
 */
public class IBinderProxy implements InvocationHandler {
    private IBinder binderOrigin;
    private IPackageManager packageManagerProxy;
    private ClassLoader classLoader;

    public IBinderProxy(IBinder binderOrigin, IPackageManager packageManagerProxy, ClassLoader classLoader) {
        this.binderOrigin = binderOrigin;
        this.packageManagerProxy = packageManagerProxy;
        this.classLoader = classLoader;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            Log.d("cjf", "queryLocalInterface:" + Arrays.toString(args));
            if (args.length == 1 && "android.content.pm.IPackageManager".equals(args[0])) {
                return packageManagerProxy;
            }
        }
        return method.invoke(binderOrigin, args);
    }
}
