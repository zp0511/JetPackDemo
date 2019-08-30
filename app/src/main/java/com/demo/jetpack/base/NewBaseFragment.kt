package com.demo.jetpack.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.jetpack.R
import com.demo.jetpack.util.StatusBarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Created by zp on 2019/8/16.
 */
abstract class NewBaseFragment : Fragment(), CoroutineScope by MainScope() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (needTransparentStatus())
            StatusBarUtil.setColor(requireActivity(), ContextCompat.getColor(requireContext(), R.color.color_ffffff), 0)
        initFragment(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    abstract fun getLayoutId(): Int

    abstract fun initFragment(view: View, savedInstanceState: Bundle?)

    protected open fun needTransparentStatus(): Boolean = true

    fun <T : ViewModel> getViewModel(clazz: Class<T>): T = ViewModelProvider(this).get(clazz)

    fun <T : ViewModel> getSharedViewModel(clazz: Class<T>): T = ViewModelProvider(requireActivity()).get(clazz)

    /**
     * 权限申请，依赖的 activity 需继承 [BaseActivity]
     */
    fun onRuntimePermissionRequest(
        permissions: Array<String>, listener: PermissionListener
    ) = if (activity != null && activity is TopBaseActivity) {
        (activity as TopBaseActivity).onRuntimePermissionsAsk(permissions, listener)
    } else {
        throw RuntimeException("Binned activity is not [BaseActivity], and check it")
    }
}