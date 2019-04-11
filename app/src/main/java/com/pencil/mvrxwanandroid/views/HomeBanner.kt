package com.pencil.mvrxwanandroid.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.pencil.mvrxwanandroid.R
import com.pencil.mvrxwanandroid.api.Banner

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class HomeBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val banner by lazy { findViewById<BGABanner>(R.id.banner) }

    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            imageView.apply {
                Glide.with(context).clear(this)
                val options = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.bg_placeholder)
                Glide.with(context).load(feedImageUrl).transition(DrawableTransitionOptions().crossFade())
                    .apply(options)
                    .into(this)
            }
        }
    }

    init {
        inflate(context, R.layout.item_home_banner, this)
    }

    @ModelProp
    fun setBanners(banners: List<Banner>) {

         banners.map { banner ->  }

        banner.apply {
            setAutoPlayAble(banners.size >1)
            setData(banners.map { it.imagePath },banners.map { it.title })
            setAdapter(bannerAdapter)
        }


    }
}