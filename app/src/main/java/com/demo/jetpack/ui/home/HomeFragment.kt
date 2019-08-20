package com.demo.jetpack.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demo.jetpack.R
import com.demo.jetpack.base.BaseFragment
import com.demo.jetpack.base.OnItemClickListener
import com.demo.jetpack.databinding.FragmentHomeBinding
import com.demo.jetpack.databinding.SlideLayoutBinding
import com.demo.jetpack.ui.activity.LoginActivity
import com.demo.jetpack.ui.web.WebViewFragment
import com.demo.jetpack.util.PreferencesHelper
import com.demo.jetpack.util.toast.ToastUtils
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by zp on 2019/8H/9.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val mViewModel: HomeModel by lazy {
        ViewModelProvider(requireActivity(), HomeModelFactory(HomeRepository())).get(HomeModel::class.java)
    }

    private val adapter: HomeArticleAdapter  by lazy {
        HomeArticleAdapter()
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        mViewModel.localArticle()

        val slideHeader =
            DataBindingUtil.inflate<SlideLayoutBinding>(layoutInflater, R.layout.slide_layout, navigationView, false)
        navigationView.addHeaderView(slideHeader.root)
        mBinding.adapter = adapter

        mBinding.itemClick = OnItemClickListener { position, view ->
            WebViewFragment.detail(mNavController, R.id.action_web_view, adapter.getItemData(position)?.link ?: "")
        }
        mViewModel.getArticle()
        mViewModel.articleList?.observe(this, Observer {
            adapter.submitList(it)
        })

        navigationView.setNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.login_out -> mViewModel.loginOut({
                    PreferencesHelper.saveCookie(requireContext(), "")
                    PreferencesHelper.saveUserId(requireContext(), 0)
                    startActivity(Intent(requireActivity(), LoginActivity::class.java))
                    requireActivity().finish()
                }, {
                    ToastUtils.show(it)
                })
            }
            true
        }
    }
}