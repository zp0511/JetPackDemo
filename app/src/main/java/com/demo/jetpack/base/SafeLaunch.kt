package com.demo.jetpack.base

import com.demo.jetpack.util.LogUtils
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @description 解决协程处理网络请求不能处理异常
 */
private val loggingExceptionHandler: CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
        LogUtils.error(throwable.message)
    }

private val handlerContext: CoroutineContext = loggingExceptionHandler + GlobalScope.coroutineContext

fun CoroutineScope.safeLaunch(block: suspend () -> Unit): Job = launch(handlerContext) { block() }