package com.pencil.mvrxwanandroid.di

import com.pencil.mvrxwanandroid.BuildConfig
import com.pencil.mvrxwanandroid.api.ApiService
import com.pencil.mvrxwanandroid.constant.Constant
import com.pencil.mvrxwanandroid.constant.HttpConstant
import com.pencil.mvrxwanandroid.core.MvRxWanAndroidApplication
import com.pencil.mvrxwanandroid.http.interceptor.CacheInterceptor
import com.pencil.mvrxwanandroid.http.interceptor.HeaderInterceptor
import com.pencil.mvrxwanandroid.http.interceptor.SaveCookieInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


fun getOkHttpClient(): OkHttpClient {

    val builder = OkHttpClient().newBuilder()
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }

    //设置 请求的缓存的大小跟位置
    val cacheFile = File(MvRxWanAndroidApplication.context.cacheDir, "cache")
    val cache = Cache(cacheFile, HttpConstant.MAX_CACHE_SIZE)

    builder.apply {
        addInterceptor(httpLoggingInterceptor)
        addInterceptor(HeaderInterceptor())
        addInterceptor(SaveCookieInterceptor())
        addInterceptor(CacheInterceptor())
        cache(cache)  //添加缓存
        connectTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true) // 错误重连

    }
    return builder.build()
}


val appModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG){
                HttpLoggingInterceptor.Level.BODY
            }else{
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single { getOkHttpClient() }

    single { get<Retrofit>().create(ApiService::class.java) }


    single {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


}


/*
var retrofit = Retrofit.Builder()
    .baseUrl("http://example.com")
    .addCallAdapterFactory(ObserveOnMainCallAdapterFactory(observeOn))
    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(io()))
    .build()*/
