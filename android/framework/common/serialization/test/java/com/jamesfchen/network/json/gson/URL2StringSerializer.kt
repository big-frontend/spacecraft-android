package com.jamesfchen.network.json.gson

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.net.URL

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/16/2019  Fri
 */
class URL2StringSerializer : JsonSerializer<URL> {
    override fun serialize(src: URL?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val primitive = JsonPrimitive(src.toString())
        println("serialize${primitive.asString}")
        return primitive
    }

}