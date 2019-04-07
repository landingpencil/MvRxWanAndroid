package com.pencil.mvrxwanandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_fragment,
                R.id.knowledge_fragment,
                R.id.wechat_fragment,
                R.id.navigation_fragment,
                R.id.project_fragment
            ), drawer_layout
        )

        toolbar.setupWithNavController(navController, appBarConfiguration)

        bottom_navigation_view.setupWithNavController(navController)
        nav_view.setupWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
