package com.demo.jetpack.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.jetpack.R
import com.demo.jetpack.util.StatusBarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * @author kuky.
 * @description Activity 基类
 */
abstract class BaseActivity<VB : ViewDataBinding> : TopBaseActivity(), CoroutineScope by MainScope() {

    protected val mBinding: VB by lazy {
        DataBindingUtil.setContentView(this, getLayoutId()) as VB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (needTransparentStatus()) transparentStatusBar() else StatusBarUtil.setColor(
            this,
            ContextCompat.getColor(this, R.color.color_ffffff),
            0
        )
        mBinding.lifecycleOwner = this
        initActivity(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        mBinding.unbind()
    }

    /** 透明状态栏 */
    open fun transparentStatusBar() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }

    abstract fun getLayoutId(): Int

    abstract fun initActivity(savedInstanceState: Bundle?)

    protected open fun needTransparentStatus(): Boolean = false

    /** 获取 ViewModel */
    fun <T : ViewModel> getViewModel(clazz: Class<T>): T = ViewModelProvider(this).get(clazz)

}