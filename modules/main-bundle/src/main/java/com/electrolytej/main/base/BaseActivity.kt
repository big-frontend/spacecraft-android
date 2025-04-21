package com.electrolytej.main.base;

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.electrolytej.mvp.AutoDisposable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/24/2018  Wed
 * <p>
 * BaseActivity接口方便后续切换mvvm结构
 */
abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var baseActivity: BaseActivity
    //自动关闭发送的rxjava数据，防止内存泄漏
    protected lateinit var onDestroyDisposable: CompositeDisposable
    protected lateinit var onPauseDisposable: CompositeDisposable
//    protected lateinit var client: Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        client = App.getAppComponent().client()
        baseActivity = this
        onDestroyDisposable = CompositeDisposable()
        initComponent(savedInstanceState)
        handleCallback { onDestroyDisposable.add(this) }
        loadData { onDestroyDisposable.add(this) }
    }

    protected abstract fun initComponent(@Nullable savedInstanceState: Bundle?)

    protected abstract fun handleCallback(autoDisposable: AutoDisposable)
    protected abstract fun loadData(autoDisposable: AutoDisposable)

    override fun onResume() {
        super.onResume()
        onPauseDisposable = CompositeDisposable()
        onResume { onPauseDisposable.add(this) }
    }

    open protected fun onResume(autoDisposable: AutoDisposable) {

    }

    override fun onPause() {
        super.onPause()
        onPauseDisposable.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyDisposable.dispose()
    }

    /**
     * 当正在等待登入的用户press back时，需要处理正在登入的状态，因其正在等待服务器的响应，需要告知服务器
     * 客户端需要退出登入。
     * 1.Android提供的Lifecycles组件，eg.LifecycleObserver/LifecycleOwner,第三方封装库RxLifecycle
     * 2.下面这种：将back的信号传给PublishSubject，让其做判断
     */
    protected val onBackPressSubject = PublishSubject.create<Unit>()
    //    ReplaySubject （释放接收到的所有数据）
//    BehaviorSubject （释放订阅前最后一个数据和订阅后接收到的所有数据）
//    PublishSubject （释放订阅后接收到的数据）
//    AsyncSubject （仅释放接收到的最后一个数据）
//    SerializedSubject（串行Subject）
//    UnicastSubject (仅支持订阅一次的Subject)
//    TestSubject（已废弃，在2.x中被TestScheduler和TestObserver替代）
    protected val onBackPress: Observable<Unit>
        get() = onBackPressSubject

    final override fun onBackPressed() {
//        super.onBackPressed()
        onBackPressSubject.onNext(Unit)
    }

    protected fun navigateBack() = super.onBackPressed()
}
