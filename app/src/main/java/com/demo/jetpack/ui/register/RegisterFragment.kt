package com.demo.jetpack.ui.register

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.demo.jetpack.R
import com.demo.jetpack.base.BaseFragment
import com.demo.jetpack.databinding.FragmentRegisterBinding
import com.demo.jetpack.util.toast.ToastUtils

/**
 * Created by zp on 2019/8/7.
 */
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val mViewModel: RegisterViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            RegisterModelFactory(RegisterRepository())
        ).get(RegisterViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.fragment_register

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding.holder = this
        mBinding.model = mViewModel
    }

    fun register(view: View) {
        val account = mBinding.etAccount.text.toString()
        val pwd = mBinding.etPwd.text.toString()
        val rePwd = mBinding.etSurePwd.text.toString()
        if (account.isNotEmpty() && pwd.isNotEmpty() && rePwd.isNotEmpty()) {
            if (pwd != rePwd) {
                ToastUtils.show("密码不一致")
            } else {
                requireActivity().onBackPressed()
            }
        }
    }

    companion object {
        fun register(controller: NavController, @IdRes id: Int) =
            controller.navigate(id)
    }
}