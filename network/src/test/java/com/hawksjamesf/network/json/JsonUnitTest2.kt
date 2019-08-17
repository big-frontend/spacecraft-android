package com.hawksjamesf.network.json

import com.alibaba.fastjson.JSON
import com.google.gson.GsonBuilder
import com.hawksjamesf.annotations.TraceTime
import com.hawksjamesf.network.json.gson.TitlebarTypeAdapter
import com.hawksjamesf.network.printInfo
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase
import okio.buffer
import okio.source
import java.io.InputStream


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/13/2019  Tue
 */
class JsonUnitTest2 : TestCase() {

    lateinit var inputStream: InputStream

    override fun setUp() {
        super.setUp()

        inputStream = ClassLoader.getSystemResourceAsStream("github_api.json")
    }

    //    @Test
    @TraceTime
    fun testGson() {
        val gson = GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(TitleBar::class.java, TitlebarTypeAdapter())
//                .registerTypeAdapter(URL::class.java, String2URLNDeserializer())
//                .registerTypeAdapter(URL::class.java, URL2StringSerializer())
                .create()

        //反序列化
        val fromJson = gson.fromJson(inputStream.reader(), GithubApiForGson::class.java)
        //序列化
//        println(gson.toJson(fromJson))
    }

    //    @Test
    @TraceTime
    fun testFastJson() {

        val fastjson = JSON.parseObject<GithubApiForFastjson2>(inputStream.readBytes(), GithubApiForFastjson2::class.javaObjectType)
//        println(JSON.toJSONString(fastjson, SerializerFeature.PrettyFormat))

    }

    fun testMoshi(){//只能使用java 不支持kotlin data class
        val moshi = Moshi.Builder()
//                .add(HttpUrl::class.java, URLTypeAdapter())
//                .add(URLTypeAdapter2())
                .build()

        val adapter = moshi.adapter(GithubApiForMoShi::class.java)
        val jsonReader = JsonReader.of(inputStream.source().buffer())
        val githubApiForMoShi = adapter.fromJson(jsonReader)
//        printInfo("${githubApiForMoShi}")

    }

    fun testMoshiKotlon(){
        val moshi = Moshi.Builder()
//                .add(HttpUrl::class.java, URLTypeAdapter())
//                .add(URLTypeAdapter2())
                .add(KotlinJsonAdapterFactory())
                .build()

        val adapter = moshi.adapter(GithubApiForMoShi2::class.java)
        val jsonReader = JsonReader.of(inputStream.source().buffer())
        val githubApiForMoShi = adapter.fromJson(jsonReader)
        printInfo("${githubApiForMoShi}")

    }

    fun testKotlinSerializer(){

    }


}


