package com.zeroami.commonlib.mvp

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout

import com.zeroami.commonlib.R
import com.zeroami.commonlib.base.LBaseFragment
import com.zeroami.commonlib.utils.LT
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * BaseMvpFragment，实现MvpView，完成View的通用操作
 *
 * @author Zeroami
 */
abstract class LBaseMvpFragment<out P : LMvpPresenter<*>> : LBaseFragment(), LMvpView {

    /**
     * 获取MvpPresenter
     * @return
     */
    protected val mvpPresenter: P by object : ReadOnlyProperty<Any?, P> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): P {
            return presenter ?: throw NullPointerException()
        }
    }

    private var presenter: P? = null

    /**
     * 获取SwipeRefreshLayout
     * @return
     */
    protected var swipeRefreshLayout: SwipeRefreshLayout? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = createPresenter()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    override fun onViewCreated() {
        super.onViewCreated()
        swipeRefreshLayout = layoutView.findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout?
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

    override fun showError(text: CharSequence) {
        LT.show(text)
    }

    override fun handleArguments(arguments: Bundle) {
        presenter?.doHandleExtras(arguments)
    }

    override fun onInitialized() {
        presenter?.doViewInitialized()
    }
}
