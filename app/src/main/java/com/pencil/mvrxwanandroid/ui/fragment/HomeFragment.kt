package com.pencil.mvrxwanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.airbnb.mvrx.fragmentViewModel
import com.pencil.mvrxwanandroid.articalItem
import com.pencil.mvrxwanandroid.core.BaseFragment
import com.pencil.mvrxwanandroid.core.simpleController
import com.pencil.mvrxwanandroid.viewmodels.HomeState
import com.pencil.mvrxwanandroid.viewmodels.HomeViewModel
import com.pencil.mvrxwanandroid.views.LoadingRowModel_
import com.pencil.mvrxwanandroid.views.homeBanner
import kotlinx.android.synthetic.main.fragment_base_mvrx.*
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh_layout.setOnRefreshListener { viewModel.requestHomeData() }
        viewModel.selectSubscribe(HomeState::isLoading) {
            swipe_refresh_layout.isRefreshing = it
        }
    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        homeBanner {
            id(state.banners.size)
            banners(state.banners)

        }

        state.articles.forEach {
            articalItem {
                id(it.id)
                article(it)
                onClick { v ->
                    val actionGlobalWebViewFragment =
                        WebViewFragmentDirections.actionGlobalWebViewFragment(it.id, it.title, it.link)
                    v.findNavController().navigate(actionGlobalWebViewFragment)
                }
            }
        }

        LoadingRowModel_().apply {
            id("loading${state.articles.size}")
            onBind { _, _, _ -> viewModel.fetchNextPage() }
            addIf(state.articles.size != 0, epoxyController)
        }
    }


}
