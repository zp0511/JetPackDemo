package com.demo.jetpack.util

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment


val Float.px: Float get() = (this * Resources.getSystem().displayMetrics.density)

val Int.px: Int get() = ((this * Resources.getSystem().displayMetrics.density).toInt())

/**
 * 点击事件扩展方法
 */
fun View.onClick(method: (() -> Unit)?): View {
    setOnClickListener { method?.invoke() }
    return this
}

/**
 * 点击事件扩展方法
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/**
 * 设置View的可见
 */
fun View.visible(isVisible: Boolean): View {
    visibility = if (isVisible) VISIBLE else GONE
    return this
}

fun AppCompatActivity.showDialog(dialog: DialogFragment) {
    dialog.show(supportFragmentManager, "TAG")
}

fun dismissDialog(dialog: DialogFragment) {
    dialog.dismiss()
}

/**
 * 通过uri  获取文件的路径
 */
fun Uri.getRealFilePath(context: Context): String? {
    val scheme = this.scheme
    var data: String? = null
    if (scheme == null)
        data = this.path
    else if (ContentResolver.SCHEME_FILE == scheme) {
        data = this.path
    } else if (ContentResolver.SCHEME_CONTENT == scheme) {
        val cursor = context.contentResolver.query(this, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                if (index > -1) {
                    data = cursor.getString(index)
                }
            }
            cursor.close()
        }
    }
    return data
}

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()

}

fun Activity.hideKeyboard() {
    val imm =
        getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = window.currentFocus
    val windowToken = if (currentFocus != null) {
        currentFocus.windowToken
    } else {
        window.decorView.windowToken
    }
    if (windowToken != null) {
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun AppCompatActivity.transparentStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val option =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.decorView.systemUiVisibility = option
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }
}

fun AppCompatActivity.transparentStatusBar(view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val option =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.decorView.systemUiVisibility = option
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }
    view.setPadding(0, getStatusBar(), 0, 0)
}

fun Fragment.setToolBarHeight(view: View) {
    view.setPadding(0, activity?.getStatusBar() ?: 0, 0, 0)
}

fun Fragment.transparentStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val option =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        activity?.window?.decorView?.systemUiVisibility = option
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = Color.TRANSPARENT
    }
}

fun Fragment.transparentStatusBar(view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val option =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        activity?.window?.decorView?.systemUiVisibility = option
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = Color.TRANSPARENT
    }
    view.setPadding(0, getStatusBar(), 0, 0)
}

fun Fragment.getStatusBar(): Int {
    /**
     * 获取状态栏高度
     */
    var statusBarHeight = -1
    //获取status_bar_height资源的ID
    val resourceId = this.resources?.getIdentifier("status_bar_height", "dimen", "android") ?: 0
    if (resourceId > 0) {
        //根据资源ID获取响应的尺寸值
        statusBarHeight = this.resources?.getDimensionPixelSize(resourceId) ?: 0
    }
    return statusBarHeight
}

fun Activity.getStatusBar(): Int {
    /**
     * 获取状态栏高度
     */
    var statusBarHeight = -1
    //获取status_bar_height资源的ID
    val resourceId = this.resources?.getIdentifier("status_bar_height", "dimen", "android") ?: 0
    if (resourceId > 0) {
        //根据资源ID获取响应的尺寸值
        statusBarHeight = this.resources?.getDimensionPixelSize(resourceId) ?: 0
    }
    return statusBarHeight
}

fun Activity.getScreenHeight(): Int {
    val display = windowManager.defaultDisplay
    return display.height
}
