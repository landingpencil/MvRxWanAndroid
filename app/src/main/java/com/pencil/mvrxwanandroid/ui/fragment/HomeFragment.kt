package com.pencil.mvrxwanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.pencil.mvrxwanandroid.api.Article
import com.pencil.mvrxwanandroid.api.Banner
import com.pencil.mvrxwanandroid.articalItem
import com.pencil.mvrxwanandroid.core.BaseFragment
import com.pencil.mvrxwanandroid.core.simpleController
import com.pencil.mvrxwanandroid.viewmodels.HomeState
import com.pencil.mvrxwanandroid.viewmodels.HomeViewModel
import com.pencil.mvrxwanandroid.views.LoadingRowModel_
import com.pencil.mvrxwanandroid.views.basicRow
import com.pencil.mvrxwanandroid.views.homeBanner
import com.pencil.mvrxwanandroid.views.loadingRow


class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipRefreshLayout.setOnRefreshListener { viewModel.requestHomeData() }
        viewModel.selectSubscribe(HomeState::isLoading) {
            swipRefreshLayout.isRefreshing = it
        }
    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        homeBanner {
            id(state.banners.size)
            banners(state.banners)

        }

        state.articles.forEach { article: Article ->
            articalItem {
                id(article.id)
                article(article)
            }
        }

        LoadingRowModel_().apply {
            id("loading${state.articles.size}")
            onBind { _, _, _ -> viewModel.fetchNextPage() }
            addIf(state.articles.size != 0, epoxyController)
        }
    }


}
