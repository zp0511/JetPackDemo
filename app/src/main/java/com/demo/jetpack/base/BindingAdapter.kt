package com.demo.jetpack.base

import android.webkit.WebView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by zp on 2019/8/20.
 */

@BindingAdapter("bind:url")
fun bindWebUrl(webView: WebView, url: String?) {
    if (url.isNullOrBlank()) return
    webView.loadUrl(url)
}

@BindingAdapter("bind:pageItemClick")
fun bindItemClick(recyclerView: RecyclerView, listener: OnItemClickListener?) {
    val adapter = recyclerView.adapter
    if (adapter == null || adapter !is BasePagedListAdapter<*, *>) return

    adapter.setOnItemListener(listener)
}