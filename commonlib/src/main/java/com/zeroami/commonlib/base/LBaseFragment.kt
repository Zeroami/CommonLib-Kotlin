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
        return layoutView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
        //避免重复添加Fragment
        if (childFragmentManager.fragments == null || childFragmentManager.fragments.isEmpty()) {
            val firstFragment = getFirstFragment()
            if (firstFragment != null) {
                replaceFragment(firstFragment, false)
            }
        }
        isViewDestroyed = false
        initialize(savedInstanceState)
        onInitialized()
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
     * 获取Fragment容器id
     * @return
     */
    protected open val fragmentContainerId: Int
        get() = 0

    /**
     * setContentView完成
     */
    protected open fun onViewCreated() {}

    /**
     * 初始化操作
     * @param savedInstanceState
     */
    protected abstract fun initialize(savedInstanceState: Bundle?)

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

    /**
     * 获取显示的第一个Fragment
     * @return
     */
    protected open fun getFirstFragment(): Fragment? = null


    /**
     * 添加fragment
     * @param fragment
     */
    fun addFragment(fragment: Fragment?, isAddToBackStack: Boolean) {
        if (fragment != null) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.add(fragmentContainerId, fragment, fragment.javaClass.simpleName)
            if (isAddToBackStack) {
                transaction.addToBackStack(fragment.javaClass.simpleName)
            }
            transaction.commit()
        }
    }

    /**
     * 替换fragment
     * @param fragment
     */
    fun replaceFragment(fragment: Fragment?, isAddToBackStack: Boolean) {
        if (fragment != null) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(fragmentContainerId, fragment, fragment.javaClass.simpleName)
            if (isAddToBackStack) {
                transaction.addToBackStack(fragment.javaClass.simpleName)
            }
            transaction.commit()
        }
    }

    /**
     * 显示fragment
     * @param fragment
     */
    fun showFragment(fragment: Fragment?) {
        if (fragment != null) {
            childFragmentManager.beginTransaction()
                    .show(fragment)
                    .commit()
        }
    }

    /**
     * 隐藏fragment
     * @param fragment
     */
    fun hideFragment(fragment: Fragment?) {
        if (fragment != null) {
            childFragmentManager.beginTransaction()
                    .hide(fragment)
                    .commit()
        }
    }

    /**
     * 移除fragment
     * @param fragment
     */
    fun removeFragment(fragment: Fragment?) {
        if (fragment != null) {
            childFragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit()
        }
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}
