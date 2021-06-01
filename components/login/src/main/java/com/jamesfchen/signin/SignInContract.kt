package com.jamesfchen.signin;

import com.jamesfchen.uicomponent.mvp.AbstractSpacecraftActivity
import com.jamesfchen.uicomponent.mvp.AbstractSpacecraftPresenter
import com.jamesfchen.uicomponent.mvp.SpacecraftView
import com.jamesfchen.event.SignInFailedEvent
import com.jamesfchen.event.SignUpFailedEvent
import com.jamesfchen.modle.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/11/2018  Sun
 */
interface SignInContract {
    abstract class View : AbstractSpacecraftActivity<Presenter>() {
        override var presenter: SignInContract.Presenter = SignInPresenter.INSTANCE

    }

    abstract class Presenter : AbstractSpacecraftPresenter<SpacecraftView>() {
        //public API
        val stateObservable: Observable<ClientState>
            get() = stateDataSubject.map { it.state }
                    .distinctUntilChanged()

        val signInFailedEventObservable: Observable<SignInFailedEvent>
            get() = signInFailedEventSubject

        val signUpFailedEventObservable: Observable<SignUpFailedEvent>
            get() = signUpFailedEventSubject

        val state: ClientState
            get() = stateDataSubject.value?.state!!
        //private API
        private val stateDataSubject = BehaviorSubject.createDefault<StateData>(StateData())
        private val signInFailedEventSubject = PublishSubject.create<SignInFailedEvent>()
        private val signUpFailedEventSubject = PublishSubject.create<SignUpFailedEvent>()
        fun emitSignInFailedEvent(mobile: String, password: String, excep: ClientException) = signInFailedEventSubject.onNext(SignInFailedEvent(mobile, password, excep))
        fun emitSignUpFailedEvent(mobile: String, password: String, excep: ClientException) = signUpFailedEventSubject.onNext(SignUpFailedEvent(mobile, password, excep))

        //当登入/注册时，改变stateData的值时，通过stateDataSubject将登入/注册d的状态发给ui层从而更新ui
        var stateData: StateData
            get() = stateDataSubject.value!!
            set(value) = stateDataSubject.onNext(value)

        abstract fun signUp(signUpReq: SignUpReqBody): Single<Profile>

        abstract fun signIn(signInReq: SignInReqBody): Single<Profile>

        abstract fun signOut()

        abstract fun sendCode(sendCodeReq: SendCodeReqBody): Single<SendCodeRespBody>


    }

}
