package com.pencil.mvrxwanandroid.viewmodels

import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.pencil.mvrxwanandroid.api.ApiService
import com.pencil.mvrxwanandroid.api.KnowledgeTreeBody
import com.pencil.mvrxwanandroid.core.MvRxViewModel
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

data class KnowledgeState(
    val knowledgeTreeBodys: List<KnowledgeTreeBody> = emptyList(),
    val isLoading: Boolean = false
) : MvRxState

class KnowledgeViewModel(
    initialsState: KnowledgeState,
    private val apiService: ApiService
) : MvRxViewModel<KnowledgeState>(initialsState) {

    init {
        requestknowledgeTreeBodys()
    }



    fun requestknowledgeTreeBodys() = withState {
        apiService.getKnowledgeTree().subscribeOn(Schedulers.io())
            .doOnSubscribe{
                setState { copy(isLoading = true) }
            }
            .doOnComplete{
                setState { copy(isLoading = false) }
            }.execute { copy(knowledgeTreeBodys = it()?.data?: emptyList()) }
    }



    companion object : MvRxViewModelFactory<KnowledgeViewModel, KnowledgeState> {

        override fun create(viewModelContext: ViewModelContext, state: KnowledgeState): KnowledgeViewModel? {

            val apiService: ApiService by viewModelContext.activity.inject()
            return KnowledgeViewModel(state, apiService)
        }
    }
}