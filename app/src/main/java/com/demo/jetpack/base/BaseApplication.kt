package com.demo.jetpack.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.demo.jetpack.util.toast.ToastUtils

/**
 * Created by zp on 2019/8/17.
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        ToastUtils.init(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}