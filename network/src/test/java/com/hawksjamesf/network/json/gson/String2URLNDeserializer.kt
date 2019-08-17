package com.hawksjamesf.network.json.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.net.URL

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/16/2019  Fri
 */
class String2URLNDeserializer : JsonDeserializer<URL> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): URL {
        val url = URL(json?.asString)
        println("deserialize${json?.asString}")
        return url
    }


}