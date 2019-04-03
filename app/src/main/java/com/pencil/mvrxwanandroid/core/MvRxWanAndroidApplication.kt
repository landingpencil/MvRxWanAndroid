package com.pencil.mvrxwanandroid.core

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import kotlin.properties.Delegates

class MvRxWanAndroidApplication : Application() {


    companion object {
        private val TAG = "App"

        var context: Context by Delegates.notNull()
            private set

        lateinit var instance: Application


    }


    override fun onCreate() {
        super.onCreate()
         instance = this
        context = applicationContext
       startKoin {
           androidLogger(Level.DEBUG)
           androidContext(this@MvRxWanAndroidApplication)
           androidFileProperties()
           modules()
       }
    }


}



