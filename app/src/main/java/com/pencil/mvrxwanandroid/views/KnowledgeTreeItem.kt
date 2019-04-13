package com.pencil.mvrxwanandroid.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.pencil.mvrxwanandroid.R
import com.pencil.mvrxwanandroid.api.KnowledgeTreeBody
import kotlinx.android.synthetic.main.item_knowledge_tree.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class KnowledgeTreeItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.item_knowledge_tree, this)
    }

    @ModelProp
    fun setKnowledgeTree(knowledgeTree: KnowledgeTreeBody?) {
        titleFirst.text = knowledgeTree?.name
        titleSecond.text = knowledgeTree?.children?.joinToString("   ", transform = { it.name })
    }

    @CallbackProp
    fun setClickListener(listener: View.OnClickListener?) {
        cardView.setOnClickListener(listener)
    }
}