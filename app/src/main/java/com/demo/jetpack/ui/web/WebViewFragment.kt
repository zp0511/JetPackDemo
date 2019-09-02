package com.demo.jetpack.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.core.view.isVisible
import com.demo.jetpack.R
import com.demo.jetpack.base.BaseFragment
import com.demo.jetpack.databinding.FragmentWebviewBinding
import org.jetbrains.annotations.NotNull

/**
 * Created by zp on 2019/8/20.
 */
class WebViewFragment : BaseFragment<FragmentWebviewBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_webview

    @SuppressLint("SetJavaScriptEnabled")
    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding.url = activity?.intent?.getStringExtra("url")

        mBinding.webView.settings.apply {
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
        mBinding.webView.apply {
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
                    if (newProgress > 70) mBinding.pb.isVisible = false
                }
            }
        }
    }

    companion object {

        fun detail(@NotNull url: String): WebViewFragment {
            val fragment = WebViewFragment()
            val bundle = Bundle()
            bundle.putString("url", url)
            fragment.arguments = bundle
            return fragment
        }
    }
}