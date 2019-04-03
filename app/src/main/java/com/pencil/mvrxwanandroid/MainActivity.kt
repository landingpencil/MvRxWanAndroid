package com.pencil.mvrxwanandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pencil.mvrxwanandroid.api.ApiService
import com.pencil.mvrxwanandroid.api.Banner
import com.pencil.mvrxwanandroid.api.HttpResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import  kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

  val  apiservice : ApiService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)













/*        apiservice.getBanners().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            Consumer { textcontent.text = it.toString() })*/






    }
}
