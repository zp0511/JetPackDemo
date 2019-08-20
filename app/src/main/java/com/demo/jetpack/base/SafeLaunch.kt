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

/*
* launch 扩展函数用来创建一个不阻塞当前线程的 Coroutine，返回一个 Job 对象来管理这个 Coroutine 实例。调用 Job.cancel 函数可以取消这个 Coroutine 的执行
* */
fun CoroutineScope.safeLaunch(block: suspend () -> Unit): Job = launch(handlerContext) { block() }