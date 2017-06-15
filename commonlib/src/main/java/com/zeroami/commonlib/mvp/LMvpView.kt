package com.zeroami.commonlib.mvp

/**
 * MVP的View接口
 *
 * @author Zeroami
 */
interface LMvpView {

    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 显示提示信息
     * @param text 提示消息
     */
    fun showMessage(text: CharSequence)

}
