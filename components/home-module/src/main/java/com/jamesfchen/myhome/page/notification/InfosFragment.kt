package com.jamesfchen.myhome.page.notification

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jamesfchen.myhome.databinding.FragmentInfosBinding
import com.jamesfchen.pay.CPay
import com.jamesfchen.pay.IPayCallback
import com.jamesfchen.pay.WXPayInfo

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
            CPay.wxpay(requireActivity(),
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