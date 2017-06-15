package com.zeroami.commonlib.mvp

import io.reactivex.disposables.Disposable

/**
 * Rx支持相关功能接口
 *
 * @author Zeroami
 */
interface LRxSupport {

    /**
     * 添加一个订阅
     * @param disposable
     */
    fun addDisposable(disposable: Disposable)
}
