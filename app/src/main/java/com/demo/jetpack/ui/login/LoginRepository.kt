package com.demo.jetpack.ui.login

import com.demo.jetpack.network.RetrofitManager
import com.demo.jetpack.util.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by zp on 2019/8/16.
 */
class LoginRepository {
    /*
    * suspend 套起函数
    * */
    suspend fun login(userName: String, passWord: String) = withContext(Dispatchers.IO){
        val response = RetrofitManager.apiService.login(userName, passWord)
        LogUtils.debug(response)
    }
}