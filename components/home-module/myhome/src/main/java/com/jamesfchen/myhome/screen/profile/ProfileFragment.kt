package com.jamesfchen.myhome.screen.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.FragmentProfileBinding
import com.jamesfchen.myhome.databinding.ViewstubTagSampleBinding
import com.jamesfchen.myhome.screen.profile.vm.ProfileViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*

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
            }

            binding.btInflate.setOnClickListener {
//                viewStubBinding =
//                    ViewstubTagSampleBinding.bind(binding.somethingViewstub.inflate())
//                viewStubBinding.btViewstub.textr
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
        //启动一个协程，该协程内部的挂起函数不会阻塞主线程
        lifecycleScope.launch {
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
        updateSettingWidget()
        lifecycleScope.launch {
            val c = produce<Int> {
                repeat(100) {
                    send(it)
                }
                close()
            }
            val f = flow {
                for (i in 1..3) {
                    delay(500)
                    emit(i)
                }
            }
            withTimeoutOrNull(1600) {
                f.collect {
                    delay(500)
                    Log.d("cjf", "coume ${it}")
                }
            }
            Log.d("cjf", "cancel")
        }
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