package com.demo.jetpack.network

import com.demo.jetpack.entity.WanUserEntity
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by zp on 2019/8/16.
 */
interface ApiService {
    /*
    * 登录
    * */
    @POST("/user/login")
    @FormUrlEncoded
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Response<WanUserEntity>
}