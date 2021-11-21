package com.jamesfchen.ibc.router

import androidx.collection.ArrayMap
import org.json.JSONObject
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * author: jamesfchen
 * since: Jul/02/2021  Fri
 *
 * xml的种类
 * - 基于事件流(sax流)，不用一次性读取文档：org.xml.sax 和 org.xmlpull.v1
 * - 基于文档(dom树),一次性读取文件到dom树：官方库javax.xml.parsers
 */
object XmlParser {
    fun parse(ist:InputStream,nodeName:String): ArrayMap<String, String> {
        val arrayMap = ArrayMap<String, String>()
        val factory = DocumentBuilderFactory.newInstance()
        val dct = factory.newDocumentBuilder()
            .parse(ist)
        val dctRoot = dct.documentElement
//        val element = dctRoot.getElementsByTagName(nodeName).item(0)
//        val elementsByTagName = dctRoot.getElementsByTagName(nodeName)

        return arrayMap
    }
}