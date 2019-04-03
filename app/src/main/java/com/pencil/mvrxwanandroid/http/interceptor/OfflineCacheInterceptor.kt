package com.pencil.mvrxwanandroid.http.interceptor

import com.pencil.mvrxwanandroid.core.MvRxWanAndroidApplication
import com.pencil.mvrxwanandroid.util.NetWorkUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @desc OfflineCacheInterceptor: 没有网时缓存
 */
class OfflineCacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (!NetWorkUtil.isNetworkAvailable(MvRxWanAndroidApplication.context)) {
            // 无网络时，设置超时为4周  只对get有用,post没有缓冲
            val maxStale = 60 * 60 * 24 * 28
            response.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("nyn")
                .build()
        }

        return response
    }
}