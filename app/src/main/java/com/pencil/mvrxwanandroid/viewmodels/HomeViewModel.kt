package com.pencil.mvrxwanandroid.viewmodels

import com.airbnb.mvrx.*
import com.pencil.mvrxwanandroid.api.*
import com.pencil.mvrxwanandroid.core.MvRxViewModel
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

private const val HOME_PER_PAGE = 20

data class HomeState(
    val articles: List<Article> = emptyList(),
    val requestArticles: Async<HttpResult<ArticleResponseBody>> = Uninitialized,
    val banners: List<Banner> = emptyList(),
    val requestBanners: Async<HttpResult<List<Banner>>> = Uninitialized,
    val isLoading: Boolean = false
) : MvRxState

class HomeViewModel(
    initialsState: HomeState,
    private val apiService: ApiService
) : MvRxViewModel<HomeState>(initialsState) {

    var bannersIsLoading = false
    var articlesIsLoading = false


    init {
        requestHomeData()
    }

    fun requestBanner() = withState { state: HomeState ->

        apiService.getBanners().subscribeOn(Schedulers.io())
            .doOnSubscribe {
                bannersIsLoading = true
                setState { copy(isLoading = bannersIsLoading && articlesIsLoading) }
            }
            .doOnComplete {
                bannersIsLoading = false
                setState { copy(isLoading = bannersIsLoading && articlesIsLoading) }
            }
            .execute {
                copy(banners = it()?.data ?: emptyList(), requestBanners = it)
            }

    }

    fun fetchNextPage() = withState { state: HomeState ->
        if (state.requestArticles is Loading) return@withState
        apiService.getArticles(state.articles.size / HOME_PER_PAGE)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                articlesIsLoading = true
                setState { copy(isLoading = bannersIsLoading && articlesIsLoading) }
            }
            .doOnComplete {
                articlesIsLoading = false
                setState { copy(isLoading = bannersIsLoading && articlesIsLoading) }
            }
            .execute { copy(requestArticles = it, articles = articles + (it()?.data?.datas ?: emptyList())) }
    }

    fun requestHomeData() {
        setState { copy(articles = emptyList(), requestArticles = Uninitialized, banners = emptyList(), requestBanners = Uninitialized ,isLoading = false) }
        requestBanner()
        fetchNextPage()


    }

    companion object : MvRxViewModelFactory<HomeViewModel, HomeState> {
        override fun create(viewModelContext: ViewModelContext, state: HomeState): HomeViewModel? {
            val apiService: ApiService by viewModelContext.activity.inject()
            return HomeViewModel(state, apiService)
        }
    }


}
