package com.jamesfchen.myhome.screen.notification

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.FragmentInfosBinding
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import java.util.concurrent.TimeUnit

class InfosFragment : Fragment() {
    lateinit var binding: FragmentInfosBinding
    val infoViewModel: InfosViewModel by viewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleScope.launchWhenResumed {
            Observable.interval(1, TimeUnit.SECONDS)
                .subscribe {
                    binding.viewmodel?.notification?.set("您有一条新消息，请注意查收 ${it}")
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInfosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = infoViewModel
    }
}