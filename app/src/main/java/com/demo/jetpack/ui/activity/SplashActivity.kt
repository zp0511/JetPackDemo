package com.demo.jetpack.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.demo.jetpack.R
import com.demo.jetpack.base.BaseActivity
import com.demo.jetpack.databinding.ActivitySplashBinding
import com.demo.jetpack.util.PreferencesHelper

/**
 * Created by zp on 2019/8/19.
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    companion object {
        val mHandler = Handler()
    }

    override fun needTransparentStatus(): Boolean {
        return true
    }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initActivity(savedInstanceState: Bundle?) {
        val hasLogin = PreferencesHelper.hasLogin(this)
        if (hasLogin) {
            mHandler.postDelayed({
                startActivity(
                    Intent(this@SplashActivity, MainActivity::class.java)
                )
                finish()
            }, 500)
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}