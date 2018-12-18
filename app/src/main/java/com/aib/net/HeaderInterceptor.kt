package com.aib.net


import android.support.v4.util.ArrayMap

import com.aib.utils.Constants
import com.blankj.utilcode.util.SPUtils

import java.io.IOException

import com.aib.utils.CryptoUtils

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Form表单拦截器
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val paramsMap = ArrayMap<String, String>()
        val token = SPUtils.getInstance().getString(Constants.TOKEN)

        val builder = chain.request().newBuilder()
                .addHeader(Constants.TOKEN, token)

        if (chain.request().body() is FormBody) {
            if (token !== "") {
                for (i in 0 until (chain.request().body() as FormBody).size()) {
                    val name = (chain.request().body() as FormBody).encodedName(i)
                    val value = (chain.request().body() as FormBody).encodedValue(i)
                    paramsMap[name] = value
                }
                try {
                    builder.addHeader("sign", CryptoUtils.codePswd(paramsMap, SPUtils.getInstance().getString(Constants.TOKEN_SCRET)))
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        return chain.proceed(builder.build())
    }
}