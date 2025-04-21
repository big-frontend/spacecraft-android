package com.jamesfchen.login

import android.app.Activity
import android.os.Bundle
import com.electrolytej.account.databinding.ActivitySigninBinding
import com.jamesfchen.login.modle.SignInReqBody

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/23/2018  Tue
 */
class SignInActivity : Activity() {
    companion object {
        private const val TAG = "SignInActivity"

    }

    lateinit var binding: ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.tvSignup.clicks()
//            .doOnNext { binding.tvSignup.isPressed = true }
//            .subscribe({
//                openActivity<SignUpActivity>(false)
//            }, { }, {}).autoDisposable()
//
//        Observables.combineLatest(binding.atvMobile.textChanges(), binding.etPassword.textChanges())
//            .map { !TextUtil.isEmpty(it.first, it.second) }
//            .subscribe { binding.btSignIn.isEnabled = it }
//            .autoDisposable()
//
//        binding.etPassword.editorActions()
//            .filter { it == EditorInfo.IME_ACTION_DONE }
//            .map { Unit }
//            .mergeWith(binding.btSignIn.clicks())
//            .publish().apply {
//                subscribe { hideSoftInput() }
//                filter {
//                    !TextUtil.isEmpty(
//                        binding.atvMobile.text.toString(),
//                        binding.etPassword.text.toString()
//                    )
//                }
//                    .subscribe {
//                        signin(
//                            SignInReqBody(
//                                binding.atvMobile.text.toString(),
//                                binding.etPassword.text.toString()
//                            )
//                        )
//                    }
//
//            }.connect(autoDisposable)
//
//
//        onBackPress.map { presenter.state == ClientState.SIGNING_IN }
//            .publish().apply {
//                filter { it }.subscribe { presenter.signOut() }.autoDisposable()
//                filter { !it }.subscribe { navigateBack() }.autoDisposable()
//
//            }.connect(autoDisposable)
    }

//    private fun isValidate(mobile: CharSequence, password: CharSequence) = mobile.isNotBlank() && password.isNotBlank()

    fun signin(signInReq: SignInReqBody) {
//        presenter.signIn(signInReq)
//            .subscribeBy(
//                onSubscribe = { presenter.stateData = StateData(signinginDisposable = it) },
//                onSuccess = {
//                    presenter.stateData = StateData(profile = it)
//                    Logger.t(TAG).d("sign in--->resp:$it \nand state:${presenter.stateData}")
//                    //todo:将新的Profile存入到数据库
//                },
//                onError = {
//                    presenter.stateData = StateData()
//                    presenter.emitSignInFailedEvent(
//                        signInReq.mobile,
//                        signInReq.password,
//                        it as ClientException
//                    )
//                }
//
//            )

    }

    override fun onResume() {
//        super.onResume(autoDisposable)
//        presenter.stateObservable.publish().apply {
//            Logger.t(TAG).d("onResume")
//            map { it == ClientState.SIGNING_IN }.observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    binding.pbSigninProgress.visibility = if (it) View.VISIBLE else View.GONE
//                }.autoDisposable()
//            map { it == ClientState.SIGNED_OUT }.subscribe { binding.llSignin.visibility }
//                .autoDisposable()
////            filter { it == ClientState.SIGNED_IN }.subscribe { openActivity<HomeActivity>() }.autoDisposable()
//            filter { it == ClientState.SIGNING_UP }.subscribe { openActivity<SignUpActivity>(false) }
//                .autoDisposable()
//
//        }.connect(autoDisposable)
//
//        presenter.signInFailedEventObservable.publish().apply {
//            map { it.mobile }.subscribe { binding.atvMobile.text }.autoDisposable()
//            map { it.password }.subscribe { binding.etPassword.text }.autoDisposable()
//            map { it.excep }.observeOn(AndroidSchedulers.mainThread())
//                .subscribe { Toast.makeText(this@SignInActivity, "$it", Toast.LENGTH_SHORT).show() }
//        }.connect(autoDisposable)
    }
}