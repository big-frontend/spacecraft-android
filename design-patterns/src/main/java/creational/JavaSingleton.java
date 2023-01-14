package creational;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/02/2018  Thu
 */

class EagerSingleton {
    //eager load-thread safe
    private static final EagerSingleton ourInstance = new EagerSingleton();

    static EagerSingleton getInstance() {
        return ourInstance;
    }

    private EagerSingleton() {
    }
}

class LazySingleton {
    //lazy load-thread safe
    private static LazySingleton ourInstance;

    static LazySingleton getInstance() {
        //LazySingleton，所以需要加锁，最为常见的就是使用双重检查
        if (ourInstance == null) {
            synchronized (LazySingleton.class) {
                if (ourInstance == null) {
                    ourInstance = new LazySingleton();
                    return ourInstance;
                }
            }
        }
        return ourInstance;
    }

    private LazySingleton() {
    }
}
