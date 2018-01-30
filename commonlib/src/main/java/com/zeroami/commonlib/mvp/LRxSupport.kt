package com.zeroami.commonlib.mvp

import io.reactivex.disposables.Disposable

/**
 * Rx支持相关功能接口
 *
 * @author Zeroami
 */
interface LRxSupport {

    /**
     * 添加订阅关系
     */
    fun addDisposable(disposable: Disposable)
}
