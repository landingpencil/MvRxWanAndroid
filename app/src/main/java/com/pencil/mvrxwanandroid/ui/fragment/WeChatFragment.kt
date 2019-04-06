package com.pencil.mvrxwanandroid.ui.fragment

import com.pencil.mvrxwanandroid.core.BaseFragment
import com.pencil.mvrxwanandroid.core.simpleController
import com.pencil.mvrxwanandroid.views.basicRow
import com.pencil.mvrxwanandroid.views.loadingRow
import java.util.*


class WeChatFragment : BaseFragment() {
    override fun epoxyController() = simpleController {


        (0..100).forEach {
            basicRow {
                id(it)
                title("WeChatFragment" + it)
            }
        }


    }
}
