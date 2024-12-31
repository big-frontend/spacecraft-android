package com.electrolytej.feeds

import com.electrolytej.feeds.widget.download.ApkDownloader
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        ApkDownloader.getInstance().addOnDownloadListener("https://test-h5e.meiyou.com/download/apk/66?isdownloadapk=1","https://test-h5e.meiyou.com/download/apk/66?aid=11d45c1506d72d55&idfa=&imei=&ip=59.57.251.162&os=3&trac",object :ApkDownloader.OnDownloadListener{

        })
        ApkDownloader.getInstance().addOnDownloadListener("https://test-h5e.meiyou.com/download/apk/66?isdownloadapk=1","https://test-h5e.meiyou.com/download/apk/66?aid=11d45c1506d72d55&idfa=&imei=&ip=59.57.251.162&os=3&trac",object :ApkDownloader.OnDownloadListener{

        })
        ApkDownloader.getInstance().addOnDownloadListener("https://test-h5e.meiyou.com/download/apk/66?isdownloadapk=1","https://test-h5e.meiyou.com/download/apk/66?aid=11d45c1506d72d55&idfa=&imei=&ip=59.57.251.162&os=3&trac",object :ApkDownloader.OnDownloadListener{

        })
        ApkDownloader.getInstance().addOnDownloadListener("https://test-h5e.meiyou.com/download/apk/66?isdownloadapk=1","https://test-h5e.meiyou.com/download/apk/66?aid=11d45c1506d72d55&idfa=&imei=&ip=59.57.251.162&os=3&trac",object :ApkDownloader.OnDownloadListener{

        })
        ApkDownloader.getInstance().addOnDownloadListener("https://test-h5e.meiyou.com/download/apk/66?isdownloadapk=1","https://test-h5e.meiyou.com/download/apk/66?aid=11d45c1506d72d55&idfa=&imei=&ip=59.57.251.162&os=3&trac",object :ApkDownloader.OnDownloadListener{

        })
        ApkDownloader.getInstance().addOnDownloadListener("https://test-h5e.meiyou.com/download/apk/66?isdownloadapk=1","https://test-h5e.meiyou.com/download/apk/66?aid=11d45c1506d72d55&idfa=&imei=&ip=59.57.251.162&os=3&trac",object :ApkDownloader.OnDownloadListener{

        })
        ApkDownloader.getInstance().addOnDownloadListener("https://test-h5e.meiyou.com/download/apk/66?isdownloadapk=1",null,object :ApkDownloader.OnDownloadListener{

        })
        ApkDownloader.getInstance().addOnDownloadListener("https://test-h5e.meiyou.com/download/apk/66?isdownloadapk=1","",object :ApkDownloader.OnDownloadListener{

        })
        ApkDownloader.getInstance().addOnDownloadListener("https://test-h5e.meiyou.com/download/apk/66?isdownloadapk=1","",object :ApkDownloader.OnDownloadListener{

        })

        ApkDownloader.getInstance().downloadListeners.forEach { (downloadKey, u) ->
            println("download:  1:${downloadKey.url}  2:${downloadKey.cardUrl}")
        }

        assertEquals(4, 2 + 2)
    }
}