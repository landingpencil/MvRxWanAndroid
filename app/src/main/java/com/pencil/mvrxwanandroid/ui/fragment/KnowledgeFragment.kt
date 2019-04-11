package com.pencil.mvrxwanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.airbnb.mvrx.fragmentViewModel
import com.pencil.mvrxwanandroid.core.BaseFragment
import com.pencil.mvrxwanandroid.core.simpleController
import com.pencil.mvrxwanandroid.viewmodels.KnowledgeState
import com.pencil.mvrxwanandroid.viewmodels.KnowledgeViewModel
import com.pencil.mvrxwanandroid.views.knowledgeTreeItem
import kotlinx.android.synthetic.main.fragment_base_mvrx.*


class KnowledgeFragment : BaseFragment() {
    private val viewModel: KnowledgeViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh_layout.setOnRefreshListener { viewModel.requestknowledgeTreeBodys() }

        viewModel.selectSubscribe(KnowledgeState::isLoading) {
            swipe_refresh_layout.isRefreshing = it
        }

    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        state.knowledgeTreeBodys.forEach {
            knowledgeTreeItem {
                id(it.id)
                knowledgeTree(it)
                clickListener{v ->
                    val actionKnowledgeFragmentToKnowledgeDetailsFragment =
                        KnowledgeFragmentDirections.actionKnowledgeFragmentToKnowledgeDetailsFragment(it.name, it)
                    v.findNavController().navigate(actionKnowledgeFragmentToKnowledgeDetailsFragment)
                }
            }
        }


    }
}
