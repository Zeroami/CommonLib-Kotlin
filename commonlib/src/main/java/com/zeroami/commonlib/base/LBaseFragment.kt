package com.zeroami.commonlib.base

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeroami.commonlib.mvp.LRxSupport
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * BaseFragment，完成共有操作，定义操作流程
 *
 * @author Zeroami
 */
abstract class LBaseFragment : Fragment(), LRxSupport {

    /**
     * 获取布局View
     * @return
     */
    protected lateinit var layoutView: View
        private set

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    /**
     * View是否已经被销毁
     * @return
     */
    protected var isViewDestroyed = false
        private set


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            handleArguments(arguments)
        }
        subscribeRxBus()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutView = inflater!!.inflate(layoutId, container, false)
        onViewCreated()
        isViewDestroyed = false
        initialize(layoutView, savedInstanceState)
        onInitialized()
        return layoutView
    }

    override fun onDestroyView() {
        isViewDestroyed = true
        super.onDestroyView()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    /**
     * 获取布局文件id
     * @return
     */
    protected abstract val layoutId: Int

    /**
     * setContentView完成
     */
    protected open fun onViewCreated() {}

    /**
     * 初始化操作
     * @param savedInstanceState
     */
    protected abstract fun initialize(layoutView: View, savedInstanceState: Bundle?)

    /**
     * 处理携带的数据

     * @param arguments
     */
    protected open fun handleArguments(arguments: Bundle) {}

    /**
     * 订阅RxBus
     */
    protected open fun subscribeRxBus() {}

    /**
     * initialize完成
     */
    protected open fun onInitialized() {}

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}
