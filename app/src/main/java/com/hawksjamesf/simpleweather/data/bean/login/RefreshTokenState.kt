package com.hawksjamesf.simpleweather.data.bean.login


enum class RefreshTokenState {

    OK,
    REFRESH_FAILURE,
    OTHERS;

//    companion object {
//        var isTokenExpired = false
//        @Synchronized
//        fun refreshTokenLocked(): RefreshTokenState {
//
//            if (!isTokenExpired) {
//                return OK
//            }
//            val profile = ProfileUtil.getProfile()
//            val clientId = if (profile!!.uid == null) {
//                "+" + profile.country_code + profile.mobile
//            } else {
//                profile.uid
//            }
//            try {
//                val res = HttpMethods.getBaseService()
//                        .refreshTokenSync(RefreshTokenReq(clientId, profile.refresh_token))
//                        .execute()
//                return when (res.code()) {
//                    HttpURLConnection.HTTP_OK -> {
//                        val refreshTokenRes = res.body()
//                        ProfileUtil.refresh(refreshTokenRes)
//                        isTokenExpired = false
//                        OK
//                    }
//                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
//                        isTokenExpired = true
//                        REFRESH_FAILURE
//                    }
//                    else -> {
//                        OTHERS
//                    }
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//            } finally {
//                LogUtil.d("afterLock, time: " + System.currentTimeMillis() + "")
//            }
//            return OTHERS
//        }
//
//
//        fun getNewRequest(request: Request): Request {
//            return request.newBuilder().removeHeader(AUTH).addHeader(AUTH, getToken()).build()
//        }
//
//
//        fun getToken(): String {
//            val dbToken = ProfileUtil.getToken()
//            return "JWT $dbToken"
//        }
//
//    }
}