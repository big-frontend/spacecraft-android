package com.jamesfchen.network.json.gson

import com.google.gson.*
import com.jamesfchen.network.json.TitleBar
import java.lang.reflect.Type

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/16/2019  Fri
 */
class TitlebarTypeAdapter : JsonSerializer<TitleBar>, JsonDeserializer<TitleBar> {
    override fun serialize(src: TitleBar?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("content", src?.content)
        jsonObject.addProperty("title", src?.title)
        println("serialize${jsonObject}")
        return jsonObject
    }
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): TitleBar {
        println("deserialize${json?.asJsonObject}")
        val jsonOject = json?.asJsonObject
        val titleBar = TitleBar()
        titleBar.content = jsonOject?.get("content")?.asString
        titleBar.title = jsonOject?.get("title")?.asString
        return titleBar
    }
}