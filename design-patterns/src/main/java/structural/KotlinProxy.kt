package structural

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/02/2018  Thu
 */
interface IBinder {
    fun transact()
}

class Binder : IBinder {
    override fun transact() {
    }
}
//function type: IBinder by binder为function type返回值类型
class BinderProxy(binder: IBinder) : IBinder by binder
//class BinderProxy(binder: IBinder) : IBinder by binder{
//    override fun transact() {
//    }
//}

class Activity {
    var proxy = BinderProxy(Binder()).transact()
}
