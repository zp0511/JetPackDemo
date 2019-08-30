package com.demo.jetpack.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.demo.jetpack.base.safeLaunch
import com.demo.jetpack.entity.ArticleDetail

/**
 * Created by zp on 2019/8/19.
 */
class HomeModel(private val repository: HomeRepository) : ViewModel() {
    var articleList: LiveData<PagedList<ArticleDetail>>? = null
    var data: MutableLiveData<List<ArticleDetail>>? = MutableLiveData()
    fun getArticle() {
        articleList =
            LivePagedListBuilder(
                HomeDataSourceFactory(repository),
                PagedList.Config
                    .Builder()
                    .setPageSize(20)
                    .setEnablePlaceholders(true)
                    .setInitialLoadSizeHint(20)
                    .build()
            ).build()
    }

    fun getHomeData() {
        viewModelScope.safeLaunch {
            data?.value = repository.homeArticle(0)
        }
    }

    fun getLocalData() {
        data?.value = repository.localArticle()
    }

    fun localArticle() {
        articleList = LivePagedListBuilder(
            HomeDataLocalSourceFactory(repository), PagedList.Config
                .Builder()
                .setPageSize(20)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .build()
        ).build()
    }

    fun loginOut(success: () -> Unit, error: (msg: String) -> Unit) {
        viewModelScope.safeLaunch {
            val repose = repository.loginOut()
            if (repose.errorCode == 0) {
                success.invoke()
            } else {
                error.invoke(repose.errorMsg)
            }
        }
    }
}