package structural;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/02/2018  Thu
 */
//===========================static proxy start=========================
interface IBinder {
    boolean transact();
}

class Binder implements IBinder {//面向服务端

    public boolean transact() {//通过native层的BpBinder注册的方法，调用该方法。
       return onTransact();
    }

    public boolean onTransact(){
        return true;
    }
}

class BinderProxy implements IBinder {//面向客户端
    public boolean transact() {
        return transactNative();
    }

    public native boolean transactNative();//通过该方法调用native层的BpBinder类
}


class Activity {//客户端
    private BinderProxy proxy = new BinderProxy();
}
//===========================static proxy end=========================


//===========================dynamic proxy start========================
class Activity2 {
    private void call(Class service) {
        Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new ProxyHandler());
    }
}

class ProxyHandler implements InvocationHandler {

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        //todo something
        return null;
    }
}
//===========================dynamic proxy end========================
