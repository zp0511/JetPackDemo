package com.demo.jetpack.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.demo.jetpack.R
import com.demo.jetpack.util.StatusBarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Created by zp on 2019/8/16.
 */
abstract class BaseFragment<VB : ViewDataBinding> : Fragment(), CoroutineScope by MainScope() {
    protected lateinit var mBinding: VB
    protected lateinit var mNavController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mNavController = NavHostFragment.findNavController(this)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.lifecycleOwner = this
        if (needTransparentStatus())
            StatusBarUtil.setColor(requireActivity(), ContextCompat.getColor(requireContext(), R.color.color_ffffff), 0)
        initFragment(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        mBinding.unbind()
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
    ) = if (activity != null && activity is BaseActivity<*>) {
        (activity as BaseActivity<*>).onRuntimePermissionsAsk(permissions, listener)
    } else {
        throw RuntimeException("Binned activity is not [BaseActivity], and check it")
    }
}