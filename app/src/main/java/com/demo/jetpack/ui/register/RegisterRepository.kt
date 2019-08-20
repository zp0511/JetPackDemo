package com.demo.jetpack.ui.register

import android.text.TextUtils
import com.demo.jetpack.base.BaseApplication
import com.demo.jetpack.network.RetrofitManager
import com.demo.jetpack.util.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by zp on 2019/8/19.
 */
class RegisterRepository {
    suspend fun register(
        account: String,
        password: String,
        success: (message: String) -> Unit,
        error: (message: String) -> Unit
    ) =
        withContext(Dispatchers.IO) {
            val response = RetrofitManager.apiService.register(account, password, password)
            if (response.body()?.errorCode == 0) {
                val cookies = StringBuilder()
                val body = response.body()
                response.headers().filter {
                    TextUtils.equals(it.first, "Set-Cookie")
                }.forEach {
                    cookies.append(it.second).append(";")
                }
                val strCookie =
                    if (cookies.endsWith(";")) cookies.substring(0, cookies.length - 1)
                    else cookies.toString()
                PreferencesHelper.saveCookie(BaseApplication.context, strCookie)
                PreferencesHelper.saveUserId(BaseApplication.context, body?.data?.id ?: 0)
                PreferencesHelper.saveUserName(BaseApplication.context, body?.data?.username ?: "")
                success.invoke(response.message())
            } else {
                error.invoke("注册失败")
            }
        }
}