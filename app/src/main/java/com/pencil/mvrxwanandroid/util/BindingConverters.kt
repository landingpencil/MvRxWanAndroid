package com.pencil.mvrxwanandroid.util

import android.view.View
import com.pencil.mvrxwanandroid.api.Article


object ConverterUtil {
    @JvmStatic
    fun chapterName(article: Article): String {
        return when {
            article.superChapterName.isNotEmpty() and article.chapterName.isNotEmpty() -> "${article.superChapterName} / ${article.chapterName}"
            article.superChapterName.isNotEmpty() -> article.superChapterName
            article.chapterName.isNotEmpty() -> article.chapterName
            else -> ""
        }
    }

    @JvmStatic fun articleTop(article: Article): Int {
        return if (article.top == "1") {
           View.VISIBLE
        } else { View.GONE
        }
    }


}

