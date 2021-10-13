package com.jamesfchen

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.SparseArray
import com.jamesfchen.loader.App
import com.jamesfchen.loader.IBinderPoolApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Copyright ® $ 2021
 * All right reserved.

 * @author jamesfchen
 * @email hawksjamesf@gmail.com
 * @since 10月/11/2021  周一
 */
class BinderPoolService : Service() {
    val sparseArray = SparseArray<IBinder>()

    init {
//        sparseArray.put(IAudioServiceApi::class.java,AudioServiceApiImpl(this))
    }

    override fun onBind(intent: Intent?): IBinder {
        return object : IBinderPoolApi.Stub() {
            override fun queryApi(apiId: Int): IBinder? {
                return sparseArray.get(apiId)
            }

        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }
}

private var binderPoolApi: IBinderPoolApi? = null
private val binderPoolCon by lazy {
    BinderPoolConnection()
}

private fun bindBinderPoolService(con: ServiceConnection) {
    App.getInstance().bindService(
        Intent(App.getInstance(), BinderPoolService::class.java),
        con,
        Context.BIND_AUTO_CREATE or Context.BIND_AUTO_CREATE
    )
}

private fun unbindBinderPoolService(con: ServiceConnection) {
    App.getInstance().unbindService(con)
}

class BinderPoolConnection : ServiceConnection {
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        binderPoolApi = IBinderPoolApi.Stub.asInterface(service)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
    }
}

/**
 *  业务模块通过该方法可以查询到远程binder
 */
suspend fun queryApi(apiId: Int): IBinder? = suspendCancellableCoroutine {coroutine->
    if (binderPoolApi == null) {
        //binder service获取api
        bindBinderPoolService(binderPoolCon)
    }
    coroutine.resume(binderPoolApi?.queryApi(apiId))
}

//suspend fun queryApi(apiId: Int): IBinder = withContext(Dispatchers.IO) {
//    if (binderPoolApi == null) {
//        //binder service获取api
//        bindBinderPoolService(binderPoolCon)
//    }
//    return api.queryApi(apiId)
//}