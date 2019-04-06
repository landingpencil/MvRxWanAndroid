package com.pencil.mvrxwanandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.pencil.mvrxwanandroid.api.ApiService


import  kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

  val  apiservice : ApiService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host)

        val appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)

        toolbar.setupWithNavController(navController, appBarConfiguration)


        bottom_navigation_view.setupWithNavController(navController)


    }
}
