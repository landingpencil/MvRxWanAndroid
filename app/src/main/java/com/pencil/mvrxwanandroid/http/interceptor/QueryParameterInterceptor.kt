package com.pencil.mvrxwanandroid.http.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @desc QueryParameterInterceptor 设置公共参数
 */
class QueryParameterInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request: Request
        val modifiedUrl = originalRequest.url().newBuilder()
            // Provide your custom parameter here
            .addQueryParameter("phoneSystem", "")
            .addQueryParameter("phoneModel", "")
            .build()
        request = originalRequest.newBuilder().url(modifiedUrl).build()
        return chain.proceed(request)
    }
}
