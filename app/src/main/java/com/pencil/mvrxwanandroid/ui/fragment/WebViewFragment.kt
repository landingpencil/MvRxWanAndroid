package com.pencil.mvrxwanandroid.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.pencil.mvrxwanandroid.R
import kotlinx.android.synthetic.main.fragment_web_view.*
import kotlinx.android.synthetic.main.fragment_web_view.view.*


class WebViewFragment : Fragment() {

    val args: WebViewFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        web_view.loadUrl(args.url)

        // web_view.addJavascriptInterface(this, "android")
        web_view.webViewClient = webViewClient
        web_view.webChromeClient = webChromeClient

//        val webSettings = web_view.settings
//        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
//        webSettings.setSupportZoom(true)
//        webSettings.builtInZoomControls = true


    }


    private val webViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            progress_bar.visibility = View.VISIBLE
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            progress_bar.visibility = View.GONE
            super.onPageFinished(view, url)
        }

    }


    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            progress_bar.progress = newProgress
            super.onProgressChanged(view, newProgress)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        web_view.apply {
            stopLoading()
            webChromeClient = null
            webViewClient = null
            destroy()
        }
        progress_bar.clearAnimation()
    }

}







