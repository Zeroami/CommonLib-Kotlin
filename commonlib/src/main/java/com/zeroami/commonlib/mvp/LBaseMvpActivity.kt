package com.zeroami.commonlib.mvp

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout

import com.zeroami.commonlib.R
import com.zeroami.commonlib.base.LBaseActivity
import com.zeroami.commonlib.utils.LT

/**
 * BaseMvpActivity，实现MvpView，完成View的通用操作
 *
 * @author Zeroami
 */
abstract class LBaseMvpActivity<P : LMvpPresenter<*>> : LBaseActivity(), LMvpView {

    /**
     * 获取MvpPresenter
     * @return
     */
    protected var mvpPresenter: P? = null
        private set

    /**
     * 获取SwipeRefreshLayout
     * @return
     */
    protected var swipeRefreshLayout: SwipeRefreshLayout? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        mvpPresenter = createPresenter()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        mvpPresenter?.detachView()
        super.onDestroy()
    }

    override fun onViewCreated() {
        super.onViewCreated()
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout) as SwipeRefreshLayout?
    }

    /**
     * 创建Presenter
     * @return
     */
    protected abstract fun createPresenter(): P?

    override fun showLoading() {
        swipeRefreshLayout?.post { swipeRefreshLayout?.isRefreshing = true }
    }

    override fun hideLoading() {
        swipeRefreshLayout?.post { swipeRefreshLayout?.isRefreshing = false }
    }

    override fun showMessage(text: CharSequence) {
        LT.show(text)
    }


    override fun handleExtras(extras: Bundle) {
        mvpPresenter?.doHandleExtras(extras)
    }

    override fun onInitialized() {
        mvpPresenter?.doViewInitialized()
    }

}
