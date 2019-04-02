package com.pencil.mvrxwanandroid.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MvRxWanAndroidApplication : Application() {



    override fun onCreate() {
        super.onCreate()
       startKoin {
           modules()
       }
    }


}



