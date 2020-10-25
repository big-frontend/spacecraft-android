package com.hawksjamesf.common

import android.util.Log
import androidx.test.runner.AndroidJUnit4
import okhttp3.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Sep/19/2019  Thu
 */
@RunWith(AndroidJUnit4::class)
class HellCallbackTest {
    val TAG = "HellCallbackTest"
    lateinit var client: OkHttpClient
    @Before
    fun setUp() {
        client = OkHttpClient.Builder()
                .build()

    }
    @Test
    fun testCallback() {
        val request = Request.Builder()
                .url(HttpUrl.parse("https://api.github.com/user"))
                .build()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                buCallback.onFailure(call, e)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                buCallback.onResponse(call, response)
//
//
//            }
//
//        })
        //五个按照顺序发送的服务
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                    }

                    override fun onResponse(call: Call, response: Response) {
                        client.newCall(request).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                            }

                            override fun onResponse(call: Call, response: Response) {
                                client.newCall(request).enqueue(object : Callback {
                                    override fun onFailure(call: Call, e: IOException) {
                                    }

                                    override fun onResponse(call: Call, response: Response) {

                                        client.newCall(request).enqueue(object : Callback {
                                            override fun onFailure(call: Call, e: IOException) {
                                            }

                                            override fun onResponse(call: Call, response: Response) {

                                                Log.i(TAG, "onResponse:5")

                                            }

                                        })
                                    }

                                })

                            }

                        })

                    }

                })

            }

        })
        Log.i(TAG, "testCallback:")
        try {
            Thread.sleep(30000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    /**
     *=================================超华丽的分割线=========================================================================
     */

    //来自bu的回调
    interface BuCallback {
        abstract fun onFailure(call: Call, e: IOException)
        @Throws(IOException::class)
        abstract fun onResponse(call: Call, response: Response)

    }

    private val buCallback: BuCallback = object : BuCallback {
        override fun onFailure(call: Call, e: IOException) {
            buCallbackTranslayer.onFailure(call, e)
        }

        override fun onResponse(call: Call, response: Response) {
            buCallbackTranslayer.onResponse(call, response)
        }
    }

    //来自bu处理层的回调
    interface BuCallbackTranslayer {
        abstract fun onFailure(call: Call, e: IOException)
        @Throws(IOException::class)
        abstract fun onResponse(call: Call, response: Response)

    }

    private val buCallbackTranslayer: BuCallbackTranslayer = object : BuCallbackTranslayer {
        override fun onFailure(call: Call, e: IOException) {
            buCallbackBusinesslayer.onFailure(call, e)

        }

        override fun onResponse(call: Call, response: Response) {
            buCallbackBusinesslayer.onResponse(call, response)
        }
    }

    //来自bu业务层的回调
    interface BuCallbackBusinesslayer {
        abstract fun onFailure(call: Call, e: IOException)
        @Throws(IOException::class)
        abstract fun onResponse(call: Call, response: Response)

    }

    private val buCallbackBusinesslayer: BuCallbackBusinesslayer = object : BuCallbackBusinesslayer {
        override fun onFailure(call: Call, e: IOException) {

        }

        override fun onResponse(call: Call, response: Response) {
        }
    }
}