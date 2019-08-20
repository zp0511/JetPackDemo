package com.demo.jetpack.entity

/**
 * Created by zp on 2019/8/19.
 */
data class BaseReponse<T>(
    var data: T? = null,
    var errorCode: Int = 0,
    var errorMsg: String = ""
)