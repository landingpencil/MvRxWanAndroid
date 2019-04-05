package com.pencil.mvrxwanandroid.ui.fragment


import com.pencil.mvrxwanandroid.core.BaseFragment
import com.pencil.mvrxwanandroid.core.simpleController
import com.pencil.mvrxwanandroid.views.loadingRow
import java.util.*


class NavigationFragment : BaseFragment() {
    override fun epoxyController() = simpleController {


        (0..100).forEach {
            loadingRow {
                id(UUID.randomUUID().toString())
            }
        }


    }
}
