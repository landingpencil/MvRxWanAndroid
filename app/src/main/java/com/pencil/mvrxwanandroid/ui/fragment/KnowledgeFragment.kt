package com.pencil.mvrxwanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.pencil.mvrxwanandroid.core.BaseFragment
import com.pencil.mvrxwanandroid.core.simpleController
import com.pencil.mvrxwanandroid.viewmodels.HomeState
import com.pencil.mvrxwanandroid.viewmodels.KnowledgeState
import com.pencil.mvrxwanandroid.viewmodels.KnowledgeViewModel
import com.pencil.mvrxwanandroid.views.basicRow
import com.pencil.mvrxwanandroid.views.knowledgeTreeItem


class KnowledgeFragment : BaseFragment() {
    private val viewModel: KnowledgeViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipRefreshLayout.setOnRefreshListener { viewModel.requestknowledgeTreeBodys() }

        viewModel.selectSubscribe(KnowledgeState::isLoading) {
            swipRefreshLayout.isRefreshing = it
        }

    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        state.knowledgeTreeBodys.forEach {
            knowledgeTreeItem {

                id(it.id)
                knowledgeTree(it)
            }
        }


    }
}
