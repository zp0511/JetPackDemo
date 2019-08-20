package com.demo.jetpack.ui.fragment

import android.os.Bundle
import android.view.View
import com.demo.jetpack.R
import com.demo.jetpack.base.BaseFragment
import com.demo.jetpack.databinding.FragmentWelcomeBinding
import com.demo.jetpack.ui.login.LoginFragment
import com.demo.jetpack.ui.register.RegisterFragment
import com.demo.jetpack.util.onClick
import com.demo.jetpack.util.transparentStatusBar
import kotlinx.android.synthetic.main.fragment_welcome.*

/**
 * Created by zp on 2019/8/8.
 */
class WelComeFragment : BaseFragment<FragmentWelcomeBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_welcome
    }

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        transparentStatusBar()
        btn_login.setOnClickListener {
            LoginFragment.login(mNavController, R.id.login)
        }
        btn_register.onClick {
            RegisterFragment.register(mNavController, R.id.register)
        }
    }
}