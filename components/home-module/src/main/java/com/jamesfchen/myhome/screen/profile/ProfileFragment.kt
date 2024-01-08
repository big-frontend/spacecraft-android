package com.jamesfchen.myhome.screen.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ScreenUtils
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.FragmentProfileBinding
import com.jamesfchen.myhome.databinding.ViewstubTagSampleBinding
import com.jamesfchen.myhome.screen.newfeeds.LargePhotoActivity
import com.jamesfchen.myhome.screen.profile.vm.ProfileViewModel
import com.jamesfchen.myhome.util.Util
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import okhttp3.Response

/**
 *
viewbinding
简化findviewbyid的方案有
- kotlin的合成方法(类型不安全，空不安全，只支持kotlin)
- viewbinding

在编译期通过扫描layout布局文件生成一个对应的ViewBinding类，编译期的时候会通过layout对代码做空安全检查与类型安全检查,检查不合法会发生编译报错，这也是其两大优点。
 */
class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    val viewStubBinding: ViewstubTagSampleBinding by lazy {
        ViewstubTagSampleBinding.bind(binding.somethingViewstub.inflate())
    }
    private val mainScope = MainScope()
    val profileViewModel by viewModels<ProfileViewModel>()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        //不要在该协程中调用ui控件，因为create的时候还没有创建布局
        lifecycleScope.launchWhenCreated {

        }
        lifecycleScope.launchWhenStarted {

        }
        lifecycleScope.launchWhenResumed {
            binding.btSettings.setOnClickListener {
//                findNavController().navigate(directions)
                findNavController().navigate(R.id.action_settings)
//                val location = IntArray(2)
//                it.getLocationOnScreen(location)
//                ProfileDetailActivity.openActivity(activity as Context, location[0],
//                    location[1],
//                    it.getHeight()
//                )
            }

            binding.btInflate.setOnClickListener {
//                viewStubBinding =
//                    ViewstubTagSampleBinding.bind(binding.somethingViewstub.inflate())
//                viewStubBinding.btViewstub.textr
                Log.d("cjf","top activity: ${Util.getI().topActivity?.get()}")
            }
            binding.tvChangeIcon.setOnClickListener {
                findNavController().navigate(R.id.action_change_icon)
            }

        }
        lifecycleScope.launch(Dispatchers.IO) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    val channel = Channel<Int>()
    override fun onResume() {
        super.onResume()
        Log.d("cjf","密度${ScreenUtils.getScreenDensity()} ${ScreenUtils.getScreenDensityDpi()}")
        //启动一个协程，该协程内部的挂起函数不会阻塞主线程
        val job = lifecycleScope.launch {
            Log.d("cjf", "step 1")
            //async挂起函数不会阻塞协程，不同于其他挂起函数。因为async会在内部启动另外一个协程
            //这样就可以实现懒加载，async在开始就启动，等到流程后面再调用awiat获取数据刷新ui
            val deferred1 = async {
                Log.d("cjf", "async1")
                return@async flow {
                    repeat(300) {
                        emit(it)
                        delay(100)
                    }
                }
            }
            val deferred2 = async {
                Log.d("cjf", "async2")
                return@async flow {
                    repeat(300) {
                        emit(it)
                        delay(100)
                    }
                }
            }
            val a = suspendCancellableCoroutine<Int>{
                it.resume(1){

                }

            }
            withContext(Dispatchers.Main) {
                Log.d("cjf", "with context flow before")
                delay(3000)
                flow {
                    repeat(100) {
                        emit(it)
                        delay(100)
                    }
                }
                Log.d("cjf", "with context after")
            }

            Log.d("cjf", "step 2")
            profileViewModel.locationDatas
                .collect { l7 ->

                    binding.tvLocation.text = "myid:${l7.myId}"
                }
            //一个请求失败了其他的请求也要继续
            supervisorScope{

            }
            //一个网络请求失败了，所有其他的请求都将被立即取消
            coroutineScope{
            //相比较launch 、 async 启动的协程会造成内存泄露，coroutineScope执行完block就会自动关闭协程，这是期优点

            }
            Log.d("cjf", "step 3")
            flow {
                repeat(100) {
                    emit(it)
                    delay(100)

                }
            }.onEmpty {
                channel.cancel()
            }
//                .flowOn(Dispatchers.IO)//更改上游数据流
                .collect {
                    channel.send(it)
                    binding.somethingInclude.bt123.text = "123_ad ${it}"
                }
//            val ret = deferred1 + deferred2
            deferred1.await().onStart {
                delay(2000)
            }.collect { num ->
                viewStubBinding.btViewstub.text = " view stub ${num}"
            }
        }
        job.invokeOnCompletion {

        }
        updateSettingWidget()
//        lifecycleScope.launch {
//            val c = produce<Int> {
//                repeat(100) {
//                    send(it)
//                }
//                close()
//            }
//            val f = flow {
//                for (i in 1..3) {
//                    delay(500)
//                    emit(i)
//                }
//            }
//            withTimeoutOrNull(1600) {
//                f.collect {
//                    delay(500)
//                    Log.d("cjf", "coume ${it}")
//                }
//            }
//            Log.d("cjf", "cancel")
//        }
        Log.d("cjf", "onResume")
    }

    fun updateSettingWidget() {
        lifecycleScope.launch {
            for (i in channel) {
                binding.btSettings.text = "settings ${i}"
            }

        }

    }

}
