package com.pencil.mvrxwanandroid.ui.fragment

import com.airbnb.mvrx.fragmentViewModel
import com.pencil.mvrxwanandroid.api.Article
import com.pencil.mvrxwanandroid.articalItem
import com.pencil.mvrxwanandroid.core.BaseFragment
import com.pencil.mvrxwanandroid.core.simpleController
import com.pencil.mvrxwanandroid.viewmodels.HomeViewModel
import com.pencil.mvrxwanandroid.views.basicRow
import com.pencil.mvrxwanandroid.views.loadingRow


class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by fragmentViewModel()




    override fun epoxyController() = simpleController(viewModel) {state ->

        state.articles.forEach{ article: Article ->


            articalItem {
                id(article.id)
                article(article)
            }
            }
        loadingRow {
            id("loading${state.articles.size}")
            onBind { _, _, _ -> viewModel.fetchNextPage() }
        }

    }


}
