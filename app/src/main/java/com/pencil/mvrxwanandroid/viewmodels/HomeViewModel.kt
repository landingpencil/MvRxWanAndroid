package com.pencil.mvrxwanandroid.viewmodels

import com.airbnb.mvrx.*
import com.pencil.mvrxwanandroid.api.ApiService
import com.pencil.mvrxwanandroid.api.Article
import com.pencil.mvrxwanandroid.api.ArticleResponseBody
import com.pencil.mvrxwanandroid.api.HttpResult
import com.pencil.mvrxwanandroid.core.MvRxViewModel
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

private const val HOME_PER_PAGE = 20

data class HomeState(
    val articles: List<Article> = emptyList(),
    val request: Async<HttpResult<ArticleResponseBody>> = Uninitialized


) : MvRxState

class HomeViewModel(
    initialsState: HomeState,
    private val apiService: ApiService
) : MvRxViewModel<HomeState>(initialsState) {

    init {
       fetchNextPage()
    }

    fun requestBanner() {

    }

    fun fetchNextPage() = withState { state: HomeState ->
        if (state.request is Loading) return@withState
        apiService.getArticles(state.articles.size / HOME_PER_PAGE)
            .subscribeOn(Schedulers.io())
            .execute { copy(request = it, articles = articles + (it()?.data?.datas ?: emptyList())) }
    }




    fun requestHomeData() {

        requestBanner()


    }

    companion object : MvRxViewModelFactory<HomeViewModel, HomeState> {
        override fun create(viewModelContext: ViewModelContext, state: HomeState): HomeViewModel? {
            val apiService: ApiService by viewModelContext.activity.inject()
            return HomeViewModel(state, apiService)
        }
    }


}
