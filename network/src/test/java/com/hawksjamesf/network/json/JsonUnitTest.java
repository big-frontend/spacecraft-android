package com.hawksjamesf.network.json;

import com.hawksjamesf.network.PrintExtensionKt;
import com.hawksjamesf.network.ServiceApi;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.annotation.Nullable;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JsonUnitTest {

    ServiceApi serviceApi;

    @Test
    public void addition_isCorrect() {
        serviceApi = create(ServiceApi.class);
        System.out.println(
                "emitOnce result:" + serviceApi.emitOnce()//client调用
        );
        System.out.println(
                "emitTwice result:" + serviceApi.emitTwice(10, 12, 13)//client调用
        );
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {

                    @Override
                    public @Nullable
                    Object invoke(Object proxy, Method method, @Nullable Object[] args) throws InvocationTargetException, IllegalAccessException {
                        System.out.println("invoke:" + method.getDeclaringClass());

                        long start = System.currentTimeMillis();
                        //
                        if (method.getDeclaringClass() == Object.class) {//如果是Object对象则不服务
                            method.invoke(this, args);
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //
                        PrintExtensionKt.printInfo("耗时:" + (System.currentTimeMillis() - start));

                        return 2;
                    }
                });
    }
}