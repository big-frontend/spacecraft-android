package com.jamesfchen.myhome.screen.notification

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jamesfchen.export.pay.IPay
import com.jamesfchen.export.pay.IPayCallback
import com.jamesfchen.export.pay.WXPayInfo
import com.jamesfchen.ibc.cbpc.IBCCbpc
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.FragmentInfosBinding
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit

class InfosFragment : Fragment() {
    lateinit var binding: FragmentInfosBinding
    val infoViewModel: InfosViewModel by viewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInfosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
//            Observable.interval(1, TimeUnit.SECONDS)
//                .subscribe {
//                }
            infoViewModel.infoFlow.collect {
                binding.tvNotification.text = "您有一条新消息，请注意查收 ${it}"
            }
            val pay = IBCCbpc.findApi(IPay::class.java)
            pay?.wxpay(requireActivity(),
                WXPayInfo(null, null, null, null, null, null, null),
                object : IPayCallback {
                    override fun success() {
                        binding.tvNotification.text = "支付成功"
                    }

                    override fun failed(code: Int, message: String?) {
                        binding.tvNotification.text = "支付失败"
                    }

                    override fun cancel() {
                        binding.tvNotification.text = "支付取消"
                    }

                })
        }
    }
}