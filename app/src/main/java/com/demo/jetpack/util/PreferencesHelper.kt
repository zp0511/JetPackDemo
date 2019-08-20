package com.demo.jetpack.util

import android.content.Context

object PreferencesHelper {
    private const val USER_KEY_ID = "wan.user.id"
    private const val USER_KEY_NAME = "wan.user.name"
    private const val USER_KEY_COOKIE = "wan.user.cookie"
    private const val PROJECT_KEY_ID = "wan.project.id"
    private const val PROJECT_KEY_TITLE = "wan.project.title"
    private const val SEARCH_KEY_KEYWORD = "wan.search.keyword"
    private const val CACHE_KEY_BANNER = "wan.cache.banner"
    private const val CACHE_KEY_HOME_ARTICLES = "wan.cache.home.articles"

    fun saveUserId(context: Context, id: Int) =
        SPUtils.saveInteger(context, USER_KEY_ID, id)

    fun hasLogin(context: Context) =
        SPUtils.getInteger(context, USER_KEY_ID) > 0

    fun saveUserName(context: Context, name: String) =
        SPUtils.saveString(context, USER_KEY_NAME, name)

    fun fetchUserName(context: Context) =
        SPUtils.getString(context, USER_KEY_NAME)

    fun saveCookie(context: Context, cookie: String) =
        SPUtils.saveString(context, USER_KEY_COOKIE, cookie)

    fun fetchCookie(context: Context) =
        SPUtils.getString(context, USER_KEY_COOKIE)

    fun saveHomeArticleCache(context: Context, articleJson: String) =
        SPUtils.saveString(context, CACHE_KEY_HOME_ARTICLES, articleJson)

    fun fetchHomeArticleCache(context: Context) =
        SPUtils.getString(context, CACHE_KEY_HOME_ARTICLES)
}