package com.hawksjamesf.simpleweather.data.source.mock;

import android.content.Context
import android.widget.Toast
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.hawksjamesf.simpleweather.data.bean.ListRes
import com.hawksjamesf.simpleweather.data.bean.home.WeatherData
import com.hawksjamesf.simpleweather.data.bean.login.*
import com.hawksjamesf.simpleweather.data.source.DataSource
import com.hawksjamesf.simpleweather.util.RestServiceTestHelper
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/22/2018  Mon
 */
class MockDataSource(
        private val mContext: Context,
        private val mUncertaintyParams: UncertaintyParams = UncertaintyParams()
) : DataSource {

    private var mGson: Gson
    private val mStore: Store = Store()
    private val TAG = "Server"

    data class Store(
            val records: MutableList<Record> = mutableListOf()
    )


    /*
    SHORT is completely numeric, such as 12.13.52 or 3:30pm
    MEDIUM is longer, such as Jan 12, 1952
    LONG is longer, such as January 12, 1952 or 3:30:32pm
    FULL is pretty completely specified, such as Tuesday, April 12, 1952 AD or 3:30:42pm PST.
     */
    init {

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
            override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date {
                return DateTime.parse(json?.asString, ISODateTimeFormat.dateTimeParser()).toDate()
            }
        })

        gsonBuilder.registerTypeAdapter(Date::class.java, JsonSerializer<Date> { src, typeOfSrc, context ->
            return@JsonSerializer JsonPrimitive(DateTime(src).toString())
        })
        gsonBuilder.setPrettyPrinting()
//                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
//                .serializeNulls()
//                .setDateFormat(DateFormat.LONG)
//        gsonBuilder.addDeserializationExclusionStrategy(object : ExclusionStrategy {
//            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
//                return false
//            }
//
//            override fun shouldSkipField(f: FieldAttributes?): Boolean {
//                return false
//            }
//        })
//        gsonBuilder.addSerializationExclusionStrategy(object : ExclusionStrategy {
//            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
//                return false
//            }
//
//            override fun shouldSkipField(f: FieldAttributes?): Boolean {
//                return false
//            }
//        })
//        gsonBuilder.setExclusionStrategies(object : ExclusionStrategy {
//            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
//                return false
//            }
//
//            override fun shouldSkipField(f: FieldAttributes?): Boolean {
//                return false
//            }
//        })
        mGson = gsonBuilder.create()
    }

    private fun uncertainty(): Single<Unit> {
        return Single.just(Unit)
                .uncertainNoConnectionError()
                .uncertainDelay()
                .uncertainUnknownError()
    }


    private fun <T> Single<T>.uncertainNoConnectionError(): Single<T> {
        val shouldThrow = Math.random() < mUncertaintyParams.chanceOfFailingWithNoConnectionError
        return map { if (shouldThrow) throw ClientException.NoConnection else it }
    }

    private fun <T> Single<T>.uncertainUnknownError(): Single<T> {
        val shouldThrow = Math.random() < mUncertaintyParams.chanceOfFailingWithUnknownError
        return map { if (shouldThrow) throw ClientException.Unknown else it }
    }

    private fun <T> Single<T>.uncertainDelay(): Single<T> {
        val average = mUncertaintyParams.averageResponseDelayInMillis
        val deviation = (Math.random() - 0.5) * mUncertaintyParams.responseDelayDeviationInMillis
        val delayAmount = (average + deviation).coerceAtLeast(0.0).toLong()
        return if (delayAmount != 0L) delay(delayAmount, TimeUnit.MILLISECONDS) else this
    }

    data class UncertaintyParams(

            val chanceOfFailingWithNoConnectionError: Float = 0.0f,

            val chanceOfFailingWithUnknownError: Float = 0.0f,

            val averageResponseDelayInMillis: Long = 0,

            val responseDelayDeviationInMillis: Long = 0
    )

    /**
     * Single<T>	只发射单个数据或错误事件,SingleObserver只有onSuccess、onError
     * Completable 只有 onComplete 和 onError 事件
     * Maybe 可以看成是Single和Completable的结合。Maybe在没有数据发射时候subscribe会调用MaybeObserver的onComplete()，
     *+ 如果Maybe有数据发射或者调用了onError()，是不会再执行MaybeObserver的onComplete()
     * Observable<T>	能够发射0或n个数据，并以成功或错误事件终止。
     */
    override fun getCurrentWeatherDate(city: String): Single<WeatherData> {
        return uncertainty()
                .flatMapObservable {
                    Observable.just(
                            mGson.fromJson(RestServiceTestHelper.getStringFromFile(mContext, Constants.CURRENT_DATA_JSON), WeatherData::class.java)
                    )
                }

                .filter {
                    it.name == city
                }
                .singleElement()
                .doOnComplete { throw  Exception() }
                .toSingle()

    }

    override fun getFiveData(city: String): Observable<ListRes<WeatherData>> {
        val type = object : TypeToken<ListRes<WeatherData>>() {}.type
        return uncertainty()
                .flatMapObservable {
                    Observable.just(
                            mGson.fromJson<ListRes<WeatherData>>(RestServiceTestHelper.getStringFromFile(mContext, Constants.FIVE_DATA_JSON), type)
                    )
                }
                .filter { it.city.name == city }
//                .singleElement()
//                .doOnComplete { throw  Exception() }
//                .ignoreElement()

    }

    override fun sendCode(sendCodeReq: SendCodeReq): Single<SendCodeResp> {
        return uncertainty()
                .flatMapObservable { Observable.fromIterable(mStore.records) }
//                .filter(Predicate<Record> { t ->
//                    if (t.profile.mobile == sendCodeReq.mobile) {
//                        Toast.makeText(mContext, "15s内不能重复发送", Toast.LENGTH_SHORT).show()
//                        return@Predicate false
//                    } else {
//
//                        return@Predicate true
//                    }
//                })
                .map {
                    val code = UUID.randomUUID().toString().toInt()
                    val profileId = UUID.randomUUID().toString().toInt()
                    mStore.records += Record(Profile(profileId, sendCodeReq.mobile, null, null), "", code)
                    Toast.makeText(mContext, "当前的验证码为$code", Toast.LENGTH_LONG).show()
                    return@map SendCodeResp(profileId, mobile = sendCodeReq.mobile)
                }
                .singleElement()
                .doOnComplete { }
                .toSingle()
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
                    record.profile
                }
                .singleElement()
//                .doOnSuccess { throw ClientException.MobileUnavaible }
//                .ignoreElement()
                .doOnComplete { throw ClientException.VerificationCode }
                .toSingle()

    }

    val testAccount = mutableListOf(
            Record(Profile(0, "100", "100_token", "100_refresh_token"), "123456", 1),
            Record(Profile(1, "101", "101_token", "101_refresh_token"), "123456", 1),
            Record(Profile(2, "102", "102_token", "102_refresh_token"), "123456", 1),
            Record(Profile(3, "103", "103_token", "103_refresh_token"), "123456", 1)

    )

    override fun signIn(signinReq: SignInReq): Single<Profile> {
        Logger.t(TAG).d("sign in")
        return uncertainty()
                .flatMapObservable { Observable.fromIterable(testAccount) }
                .filter { it.profile.mobile == signinReq.mobile && it.password == signinReq.password }
                .map { it.profile }
                .singleElement()
                .doOnComplete { ClientException.Unauthorized }
                .toSingle()
    }

    override fun signOut() {
    }
}
