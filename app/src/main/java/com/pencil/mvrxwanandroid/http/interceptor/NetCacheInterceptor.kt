package com.pencil.mvrxwanandroid.http.interceptor

import com.pencil.mvrxwanandroid.core.MvRxWanAndroidApplication
import com.pencil.mvrxwanandroid.util.NetWorkUtil
import okhttp3.Interceptor
import okhttp3.Response

class NetCacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (NetWorkUtil.isNetworkAvailable(MvRxWanAndroidApplication.context)) {
            val maxAge = 60 * 3
            // 有网络时 设置缓存超时时间0时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .build()
        }

        return response
    }
}