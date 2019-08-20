package com.demo.jetpack.ui.activity

import android.os.Bundle
import com.demo.jetpack.R
import com.demo.jetpack.base.BaseActivity
import com.demo.jetpack.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initActivity(savedInstanceState: Bundle?) {

    }
}
