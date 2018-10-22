package com.hawksjamesf.simpleweather.data.source.mock;

import android.content.Context
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.hawksjamesf.simpleweather.data.bean.ListRes
import com.hawksjamesf.simpleweather.data.bean.WeatherData
import com.hawksjamesf.simpleweather.data.source.DataSource
import com.hawksjamesf.simpleweather.util.RestServiceTestHelper
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
        private val context: Context,
        private val uncertaintyParams: UncertaintyParams = UncertaintyParams()
) : DataSource {

    var gson: Gson

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
        gson = gsonBuilder.create()
    }

    /**
     * Single<T>	只发射单个数据或错误事件,
     * Observable<T>	能够发射0或n个数据，并以成功或错误事件终止。
     * 该方法对外不能提供空数据，当无数据是会调用doOnComplete
     */
    override fun getCurrentWeatherDate(city: String): Single<WeatherData> {
        return uncertainty()
                .flatMapObservable {
                    Observable.just(
                            gson.fromJson(RestServiceTestHelper.getStringFromFile(context, Constants.CURRENT_DATA_JSON), WeatherData::class.java)
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
                            gson.fromJson<ListRes<WeatherData>>(RestServiceTestHelper.getStringFromFile(context, Constants.FIVE_DATA_JSON), type)
                    )
                }
                .filter { it.city.name == city }
                .doOnComplete { throw  Exception() }

    }

    private fun uncertainty(): Single<Unit> {
        return Single.just(Unit)
                .uncertainNoConnectionError()
                .uncertainDelay()
                .uncertainUnknownError()
    }


    private fun <T> Single<T>.uncertainNoConnectionError(): Single<T> {
        val shouldThrow = Math.random() < uncertaintyParams.chanceOfFailingWithNoConnectionError
        return map { if (shouldThrow) throw Exception() else it }
    }

    private fun <T> Single<T>.uncertainUnknownError(): Single<T> {
        val shouldThrow = Math.random() < uncertaintyParams.chanceOfFailingWithUnknownError
        return map { if (shouldThrow) throw Exception() else it }
    }

    private fun <T> Single<T>.uncertainDelay(): Single<T> {
        val average = uncertaintyParams.averageResponseDelayInMillis
        val deviation = (Math.random() - 0.5) * uncertaintyParams.responseDelayDeviationInMillis
        val delayAmount = (average + deviation).coerceAtLeast(0.0).toLong()
        return if (delayAmount != 0L) delay(delayAmount, TimeUnit.MILLISECONDS) else this
    }

    data class UncertaintyParams(

            val chanceOfFailingWithNoConnectionError: Float = 0.0f,

            val chanceOfFailingWithUnknownError: Float = 0.0f,

            val averageResponseDelayInMillis: Long = 0,

            val responseDelayDeviationInMillis: Long = 0
    )
}
