package com.demo.jetpack.ui.fragment

import android.os.Bundle
import android.view.View
import com.demo.jetpack.R
import com.demo.jetpack.base.BaseFragment
import com.demo.jetpack.databinding.FragmentWelcomeBinding
import com.demo.jetpack.ui.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_welcome.*

/**
 * Created by zp on 2019/8/8.
 */
class WelComeFragment : BaseFragment<FragmentWelcomeBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_welcome
    }

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        btn_login.setOnClickListener {
            LoginFragment.login(mNavController,R.id.login)
        }
    }
}