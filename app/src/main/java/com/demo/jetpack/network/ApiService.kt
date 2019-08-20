package com.demo.jetpack.network

import com.demo.jetpack.entity.ArticleData
import com.demo.jetpack.entity.ArticleDetail
import com.demo.jetpack.entity.BaseReponse
import com.demo.jetpack.entity.WanUserEntity
import retrofit2.Response
import retrofit2.http.*

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

    /*
    * 注册
    * */
    @POST("/user/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String, @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Response<WanUserEntity>

    // 置顶文章
    @GET("/article/top/json")
    suspend fun topArticle(@Header("Cookie") cookie: String): BaseReponse<ArrayList<ArticleDetail>>

    // 首页文章
    @GET("/article/list/{page}/json")
    suspend fun homeArticle(@Path("page") page: Int): BaseReponse<ArticleData>

    // 退出
    @GET("/user/logout/json")
    suspend fun loginout(): BaseReponse<Any>
}