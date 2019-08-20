package com.demo.jetpack.ui.home

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.recyclerview.widget.DiffUtil
import com.demo.jetpack.R
import com.demo.jetpack.base.BaseApplication
import com.demo.jetpack.base.BasePagedListAdapter
import com.demo.jetpack.base.BaseViewHolder
import com.demo.jetpack.base.safeLaunch
import com.demo.jetpack.databinding.ItemArticleBinding
import com.demo.jetpack.entity.ArticleDetail
import com.demo.jetpack.network.RetrofitManager
import com.demo.jetpack.util.PreferencesHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*

/**
 * Created by zp on 2019/8/19.
 */
class HomeRepository {
    fun localArticle(): List<ArticleDetail>? {
        return Gson().fromJson<List<ArticleDetail>>(
            PreferencesHelper.fetchHomeArticleCache(BaseApplication.context),
            object : TypeToken<List<ArticleDetail>>() {}.type
        )
    }

    suspend fun topArticle() = withContext(Dispatchers.IO) {
        val data = RetrofitManager.apiService.topArticle(PreferencesHelper.fetchCookie(BaseApplication.context)).data
        data
    }

    suspend fun homeArticle(page: Int) = withContext(Dispatchers.IO) {
        val data = RetrofitManager.apiService.homeArticle(page).data?.datas
        if (page == 0) {
            PreferencesHelper.saveHomeArticleCache(BaseApplication.context, Gson().toJson(data))
        }
        data
    }

    suspend fun loginOut() = withContext(Dispatchers.IO) {
        val repose = RetrofitManager.apiService.loginout()
        repose
    }
}

/*
* 本地数据
* */
class HomeDataLocalSourceFactory(private val repository: HomeRepository) : DataSource.Factory<Int, ArticleDetail>() {
    override fun create(): DataSource<Int, ArticleDetail> {
        return HomeDataLocalSource(repository)
    }
}

class HomeDataLocalSource(private val repository: HomeRepository) : PageKeyedDataSource<Int, ArticleDetail>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ArticleDetail>) {
        callback.onResult(repository.localArticle() ?: arrayListOf(), null, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleDetail>) {
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleDetail>) {
    }
}

/*
* 网络数据
* */
class HomeDataSourceFactory(private val repository: HomeRepository) : DataSource.Factory<Int, ArticleDetail>() {
    override fun create(): DataSource<Int, ArticleDetail> {
        return HomeDtaSource(repository)
    }
}

class HomeDtaSource(private val repository: HomeRepository) : PageKeyedDataSource<Int, ArticleDetail>(),
    CoroutineScope by MainScope() {
    /*初始加载的数据 也就是我们直接能看见的数据*/
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ArticleDetail>) {
        safeLaunch {
            val result = ArrayList<ArticleDetail>()
            val detail = repository.topArticle()
            val article = repository.homeArticle(0)
            result.addAll(detail ?: arrayListOf())
            result.addAll(article ?: arrayListOf())
            callback.onResult(result, null, 1)
        }
    }

    /*往下滑加载的数据 每次传递的第二个参数 就是 你加载数据依赖的key*/
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleDetail>) {
        safeLaunch {
            val article = repository.homeArticle(params.key)
            article?.let { callback.onResult(it, params.key + 1) }
        }
    }

    /*往上滑加载的数据 每次传递的第二个参数 就是 你加载数据依赖的key*/
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleDetail>) {
        safeLaunch {
            val article = repository.homeArticle(params.key)
            article?.let { callback.onResult(it, params.key - 1) }
        }
    }

    override fun invalidate() {
        super.invalidate()
        cancel()
    }
}

class HomeArticleAdapter : BasePagedListAdapter<ArticleDetail, ItemArticleBinding>(DIFF_CALLBACK) {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_article
    }

    override fun setVariable(data: ArticleDetail, position: Int, holder: BaseViewHolder<ItemArticleBinding>) {
        holder.binding.detail = data
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleDetail>() {
            override fun areItemsTheSame(oldItem: ArticleDetail, newItem: ArticleDetail): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ArticleDetail, newItem: ArticleDetail): Boolean =
                oldItem == newItem
        }
    }
}
