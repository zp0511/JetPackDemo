package com.demo.jetpack.ui.login

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.demo.jetpack.R
import com.demo.jetpack.base.BaseFragment
import com.demo.jetpack.databinding.FragmentLoginBinding

/**
 * Created by zp on 2019/8/7.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val mViewModel: LoginViewModel by lazy {
        ViewModelProvider(requireActivity(), LoginModelFactory(LoginRepository()))
            .get(LoginViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.fragment_login


    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mBinding.holder = this
    }

    /*
    * view必须有 否则databinding编译不过
    * */
    fun login(view: View) {
        val account = mBinding.etAccount.text.toString()
        val pwd = mBinding.etPwd.text.toString()
        if (account.isNotEmpty() && pwd.isNotEmpty()) {
            mViewModel.login(account, pwd)
        }
    }

    companion object {
        fun login(controller: NavController, @IdRes id: Int) =
            controller.navigate(id)
    }
}