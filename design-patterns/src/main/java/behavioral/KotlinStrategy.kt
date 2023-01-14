package behavioral

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/02/2018  Thu
 */
object KotlinStrategy {
    fun readExternal(closure: () -> Unit) {
        closure()
    }

    /**
     *和java实现的strategy对比，在这里我们似乎可以发现，strategy的函数类型很大程度上等同于strategy的class
     */
    val flyweightStrategy: () -> Unit = {
        //todo something
        "hello , my name is LiMing"
    }

    class DefaultStrategy : () -> Unit {
        val name: String = "WangMei"
        val age: Int = 23
        override fun invoke() {
            //todo something
            "hello , my name is $name"
        }
    }

    //函数类型的初始化有多种，这里提供第二种
    val defaultStrategy = DefaultStrategy()
//    val defaultStrategy: () -> Unit = {
//        todo something
//        "hello , my name is WangMei"
//    }

    //more strategy...
}

class Activity {
    fun call(useFlyweightMapStorage: Boolean) = if (useFlyweightMapStorage) {
        KotlinStrategy.readExternal(KotlinStrategy.flyweightStrategy)
    } else {
        KotlinStrategy.readExternal(KotlinStrategy.defaultStrategy)
    }
}
