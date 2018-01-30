package com.zeroami.commonlib.mvp


import android.os.Bundle

/**
 * MVP的Presenter接口
 *
 * @author Zeroami
 */
interface LMvpPresenter<in V : LMvpView> {

    /**
     * 关联View
     */
    fun attachView(view: V)

    /**
     * 与View解除关联
     */
    fun detachView()

    /**
     * View初始化完成
     */
    fun doViewInitialized()

    /**
     * 处理携带数据
     */
    fun handleExtras(extras: Bundle)

    /**
     * 订阅事件
     */
    fun subscribeEvent()
}