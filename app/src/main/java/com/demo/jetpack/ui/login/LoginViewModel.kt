package com.demo.jetpack.ui.login

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.jetpack.base.safeLaunch
import com.demo.jetpack.listener.SimpleWatcher
import com.demo.jetpack.util.toast.ToastUtils

/**
 * Created by zp on 2019/8/16.
 */
class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    val n = MutableLiveData<String>()//用于观察name的值 内部提供了set和get方法
    val p = MutableLiveData<String>()

    fun login(userName: String, passWord: String, success: () -> Unit) {
        viewModelScope.safeLaunch {
            repository.login(userName, passWord, {
                ToastUtils.show("登录成功")
                success.invoke()
            }, {
                ToastUtils.show(it)
            })
        }
    }

    // SimpleWatcher 是简化了的TextWatcher
    val nameWatcher = object : SimpleWatcher() {
        override fun afterTextChanged(s: Editable) {
            super.afterTextChanged(s)

            n.value = s.toString()
        }
    }

    val pwdWatcher = object : SimpleWatcher() {
        override fun afterTextChanged(s: Editable) {
            super.afterTextChanged(s)
            //p.set(s.toString())
            p.value = s.toString()
        }
    }
}