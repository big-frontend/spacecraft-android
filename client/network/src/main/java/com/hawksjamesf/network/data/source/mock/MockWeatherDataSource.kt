package com.hawksjamesf.network.data.source.mock

import android.content.Context
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.hawksjamesf.common.util.RestServiceTestHelper
import com.hawksjamesf.network.data.bean.ListRes
import com.hawksjamesf.network.data.bean.home.WeatherData
import com.hawksjamesf.network.data.source.WeatherDataSource
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import java.lang.reflect.Type
import java.util.*

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
class MockWeatherDataSource(
        private val mContext: Context,
        mUncertaintyParams: UncertaintyParams
) : WeatherDataSource, UncertaintyConditions(mUncertaintyParams) {
    private var mGson: Gson
    private val TAG = "Server"

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
                            mGson.fromJson(RestServiceTestHelper.getStringFromFile(mContext, com.hawksjamesf.network.data.source.mock.Constants.CURRENT_DATA_JSON), WeatherData::class.java)
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
                            mGson.fromJson<ListRes<WeatherData>>(RestServiceTestHelper.getStringFromFile(mContext, com.hawksjamesf.network.data.source.mock.Constants.FIVE_DATA_JSON), type)
                    )
                }
                .filter { it.city.name == city }
//                .singleElement()
//                .doOnComplete { throw  Exception() }
//                .ignoreElement()

    }
}