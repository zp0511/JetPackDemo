package com.demo.jetpack.viewmodel

import android.content.Context
import android.content.Intent
import androidx.databinding.ObservableField
import com.demo.jetpack.base.BaseConstant
import com.demo.jetpack.ui.activity.MainActivity

/**
 * Created by zp on 2019/8/12.
 */
class LoginModel constructor(name: String, pwd: String, val context: Context) {
    val n = ObservableField<String>(name)//用于观察name的值 内部提供了set和get方法
    val p = ObservableField<String>(pwd)
    /*
    * 用户名改变的方法
    * */
    fun onNameChange(s: CharSequence) {
        n.set(s.toString())
    }

    /*
    * 密码改变的方法
    * */
    fun onPwdChange(s: CharSequence) {
        p.set(s.toString())
    }

    /*
    * 登录的方法
    * */
    fun login() {
        if (n.get().equals(BaseConstant.USER_NAME)) {
            if (p.get().equals(BaseConstant.USER_PWD)) {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}