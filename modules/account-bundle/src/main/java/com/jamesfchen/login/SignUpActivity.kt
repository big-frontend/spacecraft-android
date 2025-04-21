package com.jamesfchen.login

import android.app.Activity
import android.os.Bundle
import com.electrolytej.account.databinding.ActivitySignupBinding
import com.jamesfchen.login.modle.SendCodeRespBody
import com.jamesfchen.login.modle.SignUpReqBody

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/23/2018  Tue
 */
class SignUpActivity : Activity() {
    companion object {
        private const val TAG = "SignUpActivity"
    }

    lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.atvMobile.textChanges()
//            .map { binding.atvMobile.text.isNotEmpty() }
//            .subscribe { binding.btSendCode.isEnabled = it }
//            .autoDisposable()
//
//        binding.btSendCode.clicks()
//            .subscribe {
////                    presenter.sendCode(SendCodeReqBody(binding.atvMobile.text.toString())).subscribeBy(
////                            onSubscribe = {
////
////                            },
////                            onSuccess = {
////                                sendCodeResp = it
////
////
////                            },
////                            onError = {}
////
////                    )
//            }
//            .autoDisposable()
//
//        Observables.combineLatest(
//            binding.atvMobile.textChanges(),
//            binding.etVerificationCode.textChanges(),
//            binding.etPassword.textChanges(),
//            binding.etConfirmPassword.textChanges()) { p0, p1, p2, p3 ->
//
//            if (p3.toString().isNotEmpty() && p2.toString() != p3.toString()) {
//                binding.etConfirmPassword.error = "密码不匹配"
//            } else {
//                binding.etConfirmPassword.error = null
//                binding.btSignUp.isEnabled = !TextUtil.isEmpty(p0, p1, p2, p3)
//
//            }
//
//        }
//            .publish()
//            .connect(autoDisposable)
//
//        binding.etPassword.editorActions()
//            .filter { it == EditorInfo.IME_ACTION_DONE }
//            .map { Unit }
//            .mergeWith(binding.btSignUp.clicks())
//            .publish().apply {
//                subscribe { hideSoftInput() }
//                filter { !TextUtil.isEmpty(binding.atvMobile.text.toString(), binding.etPassword.text.toString(), binding.etConfirmPassword.toString()) }.subscribe { signup(SignUpReqBody(profileId = sendCodeResp?.profileId, password = binding.etPassword.text.toString(), verificationCode = binding.etVerificationCode.text.toString().toInt())) }
//
//            }.connect(autoDisposable)
//
//        onBackPress.map { presenter.state == ClientState.SIGNING_UP }
//            .publish().apply {
//                filter { it }.subscribe { presenter.signOut() }.autoDisposable()
//                filter { !it }.subscribe { navigateBack() }.autoDisposable()
//
//            }.connect(autoDisposable)
    }

    var sendCodeResp: SendCodeRespBody? = null

    fun signup(signUpReq: SignUpReqBody) {
//        presenter.signUp(signUpReq)
//                .subscribeBy(
//                        onError = {
//                            presenter.stateData = StateData()
//                            presenter.emitSignUpFailedEvent(binding.atvMobile.text.toString(), signUpReq.password, it as ClientException)
//                        },
//                        onSuccess = {
//                            presenter.stateData = StateData(profile = it)
//                            Logger.t(TAG).d("sign up--->resp:$it and state:${presenter.stateData}")
//                            //todo:将新的Profile存入到数据库
//                            presenter.signIn(SignInReqBody(it.mobile, signUpReq.password))
//                        },
//                        onSubscribe = { presenter.stateData = StateData(signingupDisposable = it) }
//
//                )
    }


    override fun onResume() {
//        super.onResume(autoDisposable)
        //todo:注册状态的ui变化 & 注册失败的ui变化
//        presenter.stateObservable.publish().apply {
//            //            map { it == ClientState.SIGNING_UP }.subscribe(pb_signup_progress.visibility()).autoDisposable()
//            filter { it == ClientState.SIGNED_IN || it == ClientState.SIGNING_IN }
//
//                    .subscribe { navigateUp() }.autoDisposable()
//
//        }.connect(autoDisposable)
//
//        presenter.signUpFailedEventObservable.publish().apply {
//
//
//        }.connect(autoDisposable)
    }

    fun Activity.navigateUp() = navigateUpTo(parentActivityIntent)
}