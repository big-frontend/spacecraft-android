package com.hawksjamesf.network.data.bean.signin


enum class TokenState {

    NONE,
    UNAUTHORIZED,
    OTHERS;

//    data class TokeStateData(var stateEnum: TokenState,
//                         var response: Response?)

//    companion object {
//        private val UTF8 = Charset.forName("UTF-8")
//        fun getTokenState(chain: Interceptor.Chain): TokeStateData {
//            val token = getToken()
//            return if (IsEmpty.string(token)) {
//                TokeStateData(NONE, null)
//            } else {
//                val request = chain.request()
//                        .newBuilder()
//                        .addHeader(AUTH, token)
//                        .build()
//                val response = chain.proceed(request)
//
//                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED && getTokenExpiredDetail(response) == DETAIL) {
//                    TokeStateData(UNAUTHORIZED, response)
//                } else {
//                    TokeStateData(OTHERS, response)
//                }
//            }
//        }
//
//
//        fun getToken(): String {
//            val dbToken = ProfileUtil.getToken()
//            return "JWT $dbToken"
//        }
//
//
//        private fun getTokenExpiredDetail(response: Response): String {
//            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
//                val responseBody = response.body()
//                val source = responseBody?.source()
//                source?.request(Long.MAX_VALUE)
//                val buffer = source?.buffer()
//                var charset = UTF8
//                val contentType = responseBody?.contentType();
//                if (contentType != null) charset = contentType.charset(UTF8)
//                val string = buffer?.clone()?.readString(charset)
//                return GsonUtil.getGson().fromJson<cn.gailvlun.gll.net.Response>(string, cn.gailvlun.gll.net.Response::class.java).detail
//            }
//            return ""
//        }
//    }
}