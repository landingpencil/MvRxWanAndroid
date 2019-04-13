package com.pencil.mvrxwanandroid.ui.fragment

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.navigation.findNavController
import com.airbnb.mvrx.*
import com.pencil.mvrxwanandroid.api.ApiService
import com.pencil.mvrxwanandroid.api.Article
import com.pencil.mvrxwanandroid.api.ArticleResponseBody
import com.pencil.mvrxwanandroid.api.HttpResult
import com.pencil.mvrxwanandroid.articalItem
import com.pencil.mvrxwanandroid.core.BaseFragment
import com.pencil.mvrxwanandroid.core.MvRxEpoxyController
import com.pencil.mvrxwanandroid.core.MvRxViewModel
import com.pencil.mvrxwanandroid.core.simpleController
import com.pencil.mvrxwanandroid.views.marquee
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject


data class KnowledgeContentFragmentArgs(val id: Int) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<KnowledgeContentFragmentArgs> =
            object : Parcelable.Creator<KnowledgeContentFragmentArgs> {
                override fun createFromParcel(source: Parcel): KnowledgeContentFragmentArgs =
                    KnowledgeContentFragmentArgs(source)

                override fun newArray(size: Int): Array<KnowledgeContentFragmentArgs?> = arrayOfNulls(size)
            }
    }
}

data class KnowledgeContentState(
    val id: Int,
    val requestKnowledgeContent: Async<HttpResult<ArticleResponseBody>> = Uninitialized,
    val knowledgeContents: List<Article> = emptyList()
) : MvRxState {
    constructor(args: KnowledgeContentFragmentArgs) : this(args.id)
}

class KnowledgeContentViewModel(
    initialsState: KnowledgeContentState,
    val apiService: ApiService
) : MvRxViewModel<KnowledgeContentState>(initialsState) {

    init {
        requestKnowledgeContent()
    }

    fun requestKnowledgeContent() = withState {
        apiService.getKnowledgeList(0, it.id).subscribeOn(Schedulers.io())
            .execute {
                copy(requestKnowledgeContent = it, knowledgeContents = it()?.data?.datas ?: emptyList())
            }
    }

    companion object : MvRxViewModelFactory<KnowledgeContentViewModel, KnowledgeContentState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: KnowledgeContentState
        ): KnowledgeContentViewModel {
            val apiService: ApiService by viewModelContext.activity.inject()
            return KnowledgeContentViewModel(state, apiService)
        }
    }

}


class KnowledgeContentFragment : BaseFragment() {

    val viewModel: KnowledgeContentViewModel by fragmentViewModel()

    override fun epoxyController(): MvRxEpoxyController = simpleController(viewModel) { state ->
        state.knowledgeContents.forEach {

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

    }

    companion object {
        @JvmStatic
        fun newInstance(cid: Int) =
            KnowledgeContentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MvRx.KEY_ARG, KnowledgeContentFragmentArgs(cid))
                }
            }
    }
}
