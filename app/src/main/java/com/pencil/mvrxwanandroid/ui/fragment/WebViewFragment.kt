package com.pencil.mvrxwanandroid.ui.fragment

import androidx.navigation.fragment.navArgs
import com.pencil.mvrxwanandroid.core.BaseFragment
import com.pencil.mvrxwanandroid.core.simpleController
import com.pencil.mvrxwanandroid.views.basicRow


class WebViewFragment : BaseFragment() {


    val args : WebViewFragmentArgs by navArgs()


    override fun epoxyController() = simpleController {

        (0..100).forEach {
            basicRow {
                id(it)
                title(args.title + it)
            }
        }

    }





}
