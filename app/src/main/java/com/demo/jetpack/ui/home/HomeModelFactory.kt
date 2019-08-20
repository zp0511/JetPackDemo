package com.demo.jetpack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by zp on 2019/8/19.
 */
class HomeModelFactory(private val repository: HomeRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeModel(repository) as T
    }
}