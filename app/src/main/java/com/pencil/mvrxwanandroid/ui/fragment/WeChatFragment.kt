package com.pencil.mvrxwanandroid.ui.fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.airbnb.mvrx.*
import com.pencil.mvrxwanandroid.R
import com.pencil.mvrxwanandroid.api.ApiService
import com.pencil.mvrxwanandroid.api.HttpResult
import com.pencil.mvrxwanandroid.api.WXChapterBean
import com.pencil.mvrxwanandroid.core.MvRxViewModel
import com.pencil.mvrxwanandroid.core.MvRxWanAndroidApplication
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.tablayout_viewpager.*
import org.koin.android.ext.android.inject


class WeChatFragment : BaseMvRxFragment() {
    override fun invalidate() = withState(viewModel){


        viewPager.apply {
            adapter = WeChatPagerAdapter(it.requestWXChapters()?.data?: emptyList(), getChildFragmentManager())
        }
        tabLayout.setupWithViewPager(viewPager)
    }


    val viewModel : WeChatViewModel by fragmentViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.tablayout_viewpager, container, false)



}

class WeChatPagerAdapter(val list: List<WXChapterBean>, fm:FragmentManager): FragmentStatePagerAdapter(fm) {


    val fragments = mutableListOf<Fragment>()

    init {
        fragments.clear()
        list.forEach {
            fragments.add(KnowledgeContentFragment.newInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(list[position].name, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(list[position].name)
    }
}


data class WeChatState(
    val requestWXChapters: Async<HttpResult<MutableList<WXChapterBean>>> = Uninitialized
) : MvRxState

class WeChatViewModel(
    initialsState: WeChatState, val apiService: ApiService
) : MvRxViewModel<WeChatState>(initialsState) {

    init {
        requestWXChapters()
    }


    fun requestWXChapters() = apiService.getWXChapters()
        .subscribeOn(Schedulers.io())
        .execute {
            copy(requestWXChapters = it)
        }


    companion object : MvRxViewModelFactory<WeChatViewModel, WeChatState> {
        override fun create(viewModelContext: ViewModelContext, state: WeChatState): WeChatViewModel {
            val apiService: ApiService by viewModelContext.app<MvRxWanAndroidApplication>().inject()
            return WeChatViewModel(state, apiService)
        }
    }

}
