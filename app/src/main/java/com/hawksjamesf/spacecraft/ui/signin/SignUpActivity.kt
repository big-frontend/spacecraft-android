package com.hawksjamesf.spacecraft.ui.signin

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.hawksjamesf.common.mvp.AutoDisposable
import com.hawksjamesf.common.util.TextUtil
import com.hawksjamesf.common.util.hideSoftInput
import com.hawksjamesf.common.util.subscribeBy
import com.hawksjamesf.network.data.bean.signin.*
import com.hawksjamesf.spacecraft.R
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.editorActions
import com.jakewharton.rxbinding2.widget.textChanges
import com.orhanobut.logger.Logger
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.activity_signup.*

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/23/2018  Tue
 */
class SignUpActivity : SignInContract.View() {
    private val TAG = "SignUpActivity"
    override fun initComponent(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_signup)
    }

    var sendCodeResp: SendCodeResp? = null
    override fun handleCallback(autoDisposable: AutoDisposable) {

        atv_mobile.textChanges()
                .map { atv_mobile.text.isNotEmpty() }
                .subscribe { bt_send_code.isEnabled = it }
                .autoDisposable()

        bt_send_code.clicks()
                .subscribe {
                    presenter.sendCode(SendCodeReq(atv_mobile.text.toString())).subscribeBy(
                            onSubscribe = {

                            },
                            onSuccess = {
                                sendCodeResp = it


                            },
                            onError = {}

                    )
                }
                .autoDisposable()

        Observables.combineLatest(
                atv_mobile.textChanges(),
                et_verification_code.textChanges(),
                et_password.textChanges(),
                et_confirm_password.textChanges()) { p0, p1, p2, p3 ->

            if (p3.toString().isNotEmpty() && p2.toString() != p3.toString()) {
                et_confirm_password.error = "密码不匹配"
            } else {
                et_confirm_password.error = null
                bt_sign_up.isEnabled = !TextUtil.isEmpty(p0, p1, p2, p3)

            }

        }
                .publish()
                .connect(autoDisposable)

        et_password.editorActions()
                .filter { it == EditorInfo.IME_ACTION_DONE }
                .map { Unit }
                .mergeWith(bt_sign_up.clicks())
                .publish().apply {
                    subscribe { hideSoftInput() }
                    filter { !TextUtil.isEmpty(atv_mobile.text.toString(), et_password.text.toString(), et_confirm_password.toString()) }.subscribe { signup(SignUpReq(profileId = sendCodeResp?.profileId, password = et_password.text.toString(), verificationCode = et_verification_code.text.toString().toInt())) }

                }.connect(autoDisposable)

        onBackPress.map { presenter.state == ClientState.SIGNING_UP }
                .publish().apply {
                    filter { it }.subscribe { presenter.signOut() }.autoDisposable()
                    filter { !it }.subscribe { navigateBack() }.autoDisposable()

                }.connect(autoDisposable)


    }

    fun signup(signUpReq: SignUpReq) {
        presenter.signUp(signUpReq)
                .subscribeBy(
                        onError = {
                            presenter.stateData = StateData()
                            presenter.emitSignUpFailedEvent(atv_mobile.text.toString(), signUpReq.password, it as ClientException)
                        },
                        onSuccess = {
                            presenter.stateData = StateData(profile = it)
                            Logger.t(TAG).d("sign up--->resp:$it and state:${presenter.stateData}")
                            //todo:将新的Profile存入到数据库
                            presenter.signIn(SignInReq(it.mobile, signUpReq.password))
                        },
                        onSubscribe = { presenter.stateData = StateData(signingupDisposable = it) }

                )
    }


    override fun onResume(autoDisposable: AutoDisposable) {
        super.onResume(autoDisposable)
        //todo:注册状态的ui变化 & 注册失败的ui变化
        presenter.stateObservable.publish().apply {
            //            map { it == ClientState.SIGNING_UP }.subscribe(pb_signup_progress.visibility()).autoDisposable()
            filter { it == ClientState.SIGNED_IN || it == ClientState.SIGNING_IN }

                    .subscribe { navigateUp() }.autoDisposable()

        }.connect(autoDisposable)

        presenter.signUpFailedEventObservable.publish().apply {


        }.connect(autoDisposable)
    }

    fun Activity.navigateUp() = navigateUpTo(parentActivityIntent)
}