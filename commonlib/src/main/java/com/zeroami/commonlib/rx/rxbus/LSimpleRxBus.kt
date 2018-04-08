package com.zeroami.commonlib.rx.rxbus

import io.reactivex.disposables.Disposable

fun post(event: Any) = LRxBus.post(event)

fun post(event: Any, action: String) = LRxBus.post(event, action)

fun postAction(action: String) = LRxBus.postAction(action)

fun postSticky(event: Any) = LRxBus.postSticky(event)

fun postSticky(event: Any, action: String) = LRxBus.postSticky(event, action)

fun postStickyAction(action: String) = LRxBus.postStickyAction(action)

inline fun <reified T> subscribe(noinline block: (T) -> Unit): Disposable = LRxBus.subscribe(T::class.java, block)

inline fun <reified T> subscribe(action: String, noinline block: (T) -> Unit): Disposable = LRxBus.subscribe(T::class.java, action, block)

fun subscribeAction(action: String, block: () -> Unit): Disposable = LRxBus.subscribeAction(action, block)

inline fun <reified T> subscribeSticky(isAutoRemove: Boolean, noinline block: (T) -> Unit): Disposable = LRxBus.subscribeSticky(T::class.java, isAutoRemove, block)

inline fun <reified T> subscribeSticky(action: String, isAutoRemove: Boolean, noinline block: (T) -> Unit): Disposable = LRxBus.subscribeSticky(T::class.java, action, isAutoRemove, block)

fun subscribeStickyAction(action: String, isAutoRemove: Boolean, block: () -> Unit): Disposable = LRxBus.subscribeStickyAction(action, isAutoRemove, block)
