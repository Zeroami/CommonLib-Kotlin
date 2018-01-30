package com.zeroami.commonlib.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeroami.commonlib.R
import com.zeroami.commonlib.mvp.LRxSupport
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.find


/**
 * BaseFragment，完成共有操作，定义操作流程
 *
 * @author Zeroami
 */
abstract class LBaseFragment : Fragment(), LRxSupport {

    /**
     * 获取布局View
     */
    protected lateinit var layoutView: View
        private set

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    /**
     * View是否已经被销毁
     */
    protected var isViewDestroyed = false
        private set

    private var isInit = false
    private var savedInstanceState: Bundle? = null
    private var isAfterOnCreateView = false
    private lateinit var inflater: LayoutInflater
    private var container: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            handleArguments(arguments)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflater = inflater
        this.container = container
        // 判断是否懒加载
        if (isLazyLoad) {
            // 处于完全可见、没被初始化的状态
            if (userVisibleHint && !isInit) {
                this.savedInstanceState = savedInstanceState
                layoutView = inflater.inflate(layoutId, container, false)
                isInit = true
            } else {
                layoutView = inflater.inflate(lazyLoadLayoutId, container, false)
            }
        } else {
            // 不需要懒加载
            layoutView = inflater.inflate(layoutId, container, false)
            isInit = true
        }
        isAfterOnCreateView = true
        return layoutView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        // 一旦isVisibleToUser==true即可对真正需要的显示内容进行加载
        if (isLazyLoad && isAfterOnCreateView) {
            // 可见，但还没被初始化
            if (isVisibleToUser && !isInit) {
                layoutView.find<ViewGroup>(R.id.flLazyLoadContainer).let {
                    it.removeAllViews()
                    layoutView = inflater.inflate(layoutId, it, false)
                    isInit = true
                    it.addView(layoutView)
                    contentViewCreated(savedInstanceState)
                }
            }
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isLazyLoad || userVisibleHint) {
            contentViewCreated(savedInstanceState)
        }
    }

    private fun contentViewCreated(savedInstanceState: Bundle?) {
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
        subscribeEvent()
        onInitialized()
    }

    override fun onDestroyView() {
        isViewDestroyed = true
        compositeDisposable.clear()
        super.onDestroyView()
    }

    /**
     * 获取布局文件id
     */
    protected abstract val layoutId: Int

    /**
     * 获取Fragment容器id
     */
    protected open val fragmentContainerId: Int = 0


    /**
     * 是否懒加载
     */
    protected open val isLazyLoad: Boolean = false

    /**
     * 获取LazyLoadLayoutId
     */
    protected open val lazyLoadLayoutId: Int = R.layout.fragment_lazy_load

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
     */
    protected open fun handleArguments(arguments: Bundle) {}

    /**
     * 订阅事件
     */
    protected open fun subscribeEvent() {}

    /**
     * initialize完成
     */
    protected open fun onInitialized() {}

    /**
     * 获取显示的第一个Fragment
     */
    protected open fun getFirstFragment(): Fragment? = null


    /**
     * 添加fragment
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
     */
    fun removeFragment(fragment: Fragment?) {
        if (fragment != null) {
            childFragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit()
        }
    }

    /**
     * 添加订阅关系
     */
    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}
