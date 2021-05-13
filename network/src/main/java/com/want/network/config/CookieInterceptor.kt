package com.want.network.config

import com.want.common.Constant
import com.want.common.utils.MMKVUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by jhb on 2020-01-16.
 */
class CookieInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val finalResponse: Response

        val cookies = MMKVUtil.decodeStringSet(Constant.KEY_COOKIE)

        if (cookies.isNullOrEmpty()) {
            val originResponse = chain.proceed(chain.request())

//            if (!originResponse.headers("Set-Cookie").isNullOrEmpty()) {
//                val tempCookies = hashSetOf<String>()
//                originResponse.headers("Set-Cookie").forEach {
//                    tempCookies.add(it)
//                }
//                MmkvUtil.saveCookie(tempCookies)
//            }

            finalResponse = originResponse

        } else {
            val builder = chain.request().newBuilder()
            cookies.forEach {
                it?.let { builder.addHeader("Cookie", it) }
            }
            finalResponse = chain.proceed(builder.build())
        }

        return finalResponse
    }

}