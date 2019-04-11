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
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.pencil.mvrxwanandroid.R
import com.pencil.mvrxwanandroid.api.Knowledge
import kotlinx.android.synthetic.main.fragment_knowledge_details.*


class KnowledgeDetailsFragment : Fragment() {

    val args: KnowledgeDetailsFragmentArgs by navArgs()

    lateinit var viewPagerAdapter: KnowledgePagerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPagerAdapter = KnowledgePagerAdapter(args.contentData.children, childFragmentManager)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_knowledge_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_pager.apply {
            adapter = viewPagerAdapter
            offscreenPageLimit = args.contentData.children.size
        }
        tab_layout.apply {
            setupWithViewPager(view_pager)
        }
    }


}


class KnowledgePagerAdapter(val list: List<Knowledge>, fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private val fragments = mutableListOf<Fragment>()

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
