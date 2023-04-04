package com.jamesfchen.network

import okhttp3.*


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/10/2019  Sat
 *
 *
 * Authorization/Proxy-Authorization type:
 * 1.No Auth
 * 2.Basic auth( Username & password + base64): "Basic afasdf"
 * 3.Digest Auth( Username & password + Algorithm):"Digest asdfafafs"
 * 4.Bearer Token: "Bearer  afasdffadfasffsa"
 * 5.OAuth 1.0: "OAuth asdf"
 * 6.OAuth 2.0
 * 7.Hawk Authentication: "Hawk asdfsaf"
 * 8.AWS Signature
 * 9.NTLM Authentication [Beta]
 * 10.api key:  key:value
 *
 * https://zapier.com/engineering/apikey-oauth-jwt/
 * jwt token:限制数据库查询
 * api keys :开发者快速启动
 * oauth token(Bearer、OAuth):访问其他平台的用户数据
 *
 * challenge–response authentication
 * repsonse
 * header WWW-Authenticate/Proxy-Authenticate
 * code 401
 *
 *
 */
class DefaultAuthenticator : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.request.header("Authorization").isNullOrEmpty()) {
            null
        } else {
            println("Authenticating for response: $response")
            println("Challenges: " + response.challenges())
            val challenges = response.challenges()
            challenges.forEach {
                println(it.authParams[it.scheme])
            }
            //1 basic auth
            var credential = Credentials.basic("jesse", "password1")
            //2. digest auth: https://github.com/rburgst/okhttp-digest

            //3. bearer token
            credential = "Bearer asfa"
            //4. oauth 1.0
            credential = "OAuth sadfsaf"
            //5. oauth 2.0
            credential = "asfaf"

            response.request.newBuilder()
                    .header("Authorization", credential)
                    .build()
        }

    }

}