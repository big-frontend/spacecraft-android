package com.hawksjamesf.network.data.source.mock

import android.content.Context
import android.widget.Toast
import com.hawksjamesf.network.data.bean.signin.*
import com.hawksjamesf.network.data.source.SignInDataSource
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
class MockSignInDataSource(
        private val mContext: Context,
        mUncertaintyParams: UncertaintyParams
) : SignInDataSource, UncertaintyConditions(mUncertaintyParams) {

    private val mStore: Store = Store()
    private val TAG = "Server"

    data class Store(
            var records: MutableList<Record> = mutableListOf()
    )

    init {
        val testAccount = mutableListOf(
                Record(Profile(0, "100", "100_token", "100_refresh_token"), "123456", 1),
                Record(Profile(1, "101", "101_token", "101_refresh_token"), "123456", 1),
                Record(Profile(2, "102", "102_token", "102_refresh_token"), "123456", 1),
                Record(Profile(3, "103", "103_token", "103_refresh_token"), "123456", 1)
        )
        mStore.records.clear()
        mStore.records.addAll(testAccount)
    }

    val countDown: Long = 15//TimeUnit.SECONDS
    override fun sendCode(sendCodeReq: SendCodeReq): Single<SendCodeResp> {
        Logger.t(TAG).d("send code--->sendCode")
        return uncertainty()
                .flatMapObservable { Observable.fromIterable(mStore.records) }
                .filter { it.profile.mobile == sendCodeReq.mobile }
                .singleElement()
                .doOnSuccess {
                    //Maybe：当发现服务器有该数据时，注册失败，应该给前端提供注册失败信息
                    Logger.t(TAG).d("send code--->doOnSuccess")
                    throw ClientException.MobileUnavaible

                }
                .ignoreElement()
                .doOnComplete {
                    //Completable:只会调用OnComplete和OnError
                    Logger.t(TAG).d("send code--->doOnComplete")
                }
                .toSingle {

                }
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    val code = Random().nextInt(9999)
                    val profileId = Random().nextInt(20)
                    mStore.records.add(Record(Profile(profileId, sendCodeReq.mobile, null, null), "", code))
                    Logger.t(TAG).d("send code--->map $code")
                    Toast.makeText(mContext, "${TAG}打印的验证码为${code}", Toast.LENGTH_LONG).show()
                    return@map SendCodeResp(1, mobile = sendCodeReq.mobile)
                }


    }

    override fun signUp(signUpReq: SignUpReq): Single<Profile> {
        return uncertainty()
                .flatMapObservable { Observable.fromIterable(mStore.records) }
                .filter { it.code == signUpReq.verificationCode }
                .map {
                    val record = it
                    record.profile.token = ""
                    record.profile.refreshToken = ""
                    record.password = signUpReq.password
                    val indexOf = mStore.records.indexOf(it)
                    mStore.records[indexOf] = record
                    Logger.t(TAG).d("sign up--->record:${mStore.records[indexOf]}")
                    record.profile
                }
                .singleElement()
//                .doOnSuccess { throw ClientException.MobileUnavaible }
//                .ignoreElement()
                .doOnComplete { throw ClientException.VerificationCode }
                .toSingle()

    }

    override fun signIn(signinReq: SignInReq): Single<Profile> {
        return uncertainty()
                .flatMapObservable { Observable.fromIterable(mStore.records) }
                .filter { it.profile.mobile == signinReq.mobile && it.password == signinReq.password }
                .map {
                    Logger.t(TAG).d("sign in:${it.profile}")
                    it.profile
                }
                .singleElement()
                .doOnComplete { ClientException.Unauthorized }
                .toSingle()
    }

    override fun signOut() {
    }
}