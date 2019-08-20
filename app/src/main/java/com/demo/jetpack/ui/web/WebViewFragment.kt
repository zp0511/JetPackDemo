package com.demo.jetpack.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.navigation.NavController
import com.demo.jetpack.R
import com.demo.jetpack.base.BaseFragment
import com.demo.jetpack.databinding.FragmentWebviewBinding
import kotlinx.android.synthetic.main.fragment_webview.view.*

/**
 * Created by zp on 2019/8/20.
 */
class WebViewFragment : BaseFragment<FragmentWebviewBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_webview

    @SuppressLint("SetJavaScriptEnabled")
    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding.url = arguments?.getString("url")

        view.webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            allowFileAccess = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportMultipleWindows(true)
            setGeolocationEnabled(true)
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            setAppCacheEnabled(true)
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
        }
        view.webView.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }

                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    view?.loadUrl(request?.url.toString())
                    return true
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(web: WebView?, newProgress: Int) {
                    super.onProgressChanged(web, newProgress)
                    if (newProgress > 70) view.pb.isVisible = false
                }
            }
        }
    }

    companion object {
        fun detail(controller: NavController, @IdRes id: Int, url: String) {
            if (url.isBlank()) return
            controller.navigate(id, Bundle().apply { putString("url", url) })
        }
    }
}