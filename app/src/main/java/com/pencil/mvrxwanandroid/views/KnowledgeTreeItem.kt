package com.pencil.mvrxwanandroid.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.pencil.mvrxwanandroid.R
import com.pencil.mvrxwanandroid.api.KnowledgeTreeBody

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class KnowledgeTreeItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val titleFirst by lazy { findViewById<TextView>(R.id.title_first) }
    private val titleSecond by lazy { findViewById<TextView>(R.id.title_second) }


    init {
        inflate(context, R.layout.item_knowledge_tree, this)
    }

    @ModelProp
    fun setKnowledgeTree(knowledgeTree: KnowledgeTreeBody?) {


        titleFirst.text = knowledgeTree?.name

        titleSecond.text = knowledgeTree?.children?.joinToString("   ", transform = { it.name })


    }
}