package com.demo.jetpack.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.jetpack.base.safeLaunch

/**
 * Created by zp on 2019/8/19.
 */
class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {
    val n = MutableLiveData<String>("")
    val p = MutableLiveData<String>("")
    val r = MutableLiveData<String>("")


    fun onNameChanged(s: CharSequence) {
        //n.set(s.toString())
        n.value = s.toString()
    }

    fun onPwdChanged(s: CharSequence) {
        //n.set(s.toString())
        p.value = s.toString()
    }

    fun onRePwdChanged(s: CharSequence) {
        //n.set(s.toString())
        r.value = s.toString()
    }

    fun register(account: String, pwd: String, success: () -> Unit, error: (message: String) -> Unit) {
        viewModelScope.safeLaunch {
            repository.register(account, pwd, {
                success.invoke()
            }, {
                error.invoke(it)
            })
        }
    }
}