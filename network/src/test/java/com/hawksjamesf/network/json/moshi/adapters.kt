package com.hawksjamesf.network.json.moshi

import com.squareup.moshi.*
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl


class URLTypeAdapter2 {
    @FromJson
    fun string2URL(urlString: String) = urlString.toHttpUrl()

    @ToJson
    fun url2String(url: HttpUrl) = url.toString()

}

class URLTypeAdapter : JsonAdapter<HttpUrl>() {
    override fun toJson(writer: JsonWriter, value: HttpUrl?) {

    }

    override fun fromJson(reader: JsonReader): HttpUrl {
        return HttpUrl.Builder().build()
    }
}


