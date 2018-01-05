package com.zeroami.commonlib.base


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.zeroami.commonlib.mvp.LRxSupport
import com.zeroami.commonlib.library.swipebacklayout.SwipeBackActivity
import com.zeroami.commonlib.utils.LActivityUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * BaseActivity，完成共有操作，定义操作流程
 *
 * @author Zeroami
 */
abstract class LBaseActivity : SwipeBackActivity(), LRxSupport {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LActivityUtils.addActivity(this)
        if (intent != null) {
            handleIntent(intent)
            if (intent.extras != null) {
                handleExtras(intent.extras)
            }
        }
        if (isFullScreenLayout) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val window = window
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
        }
        onSetContentViewBefore()
        setContentView(layoutId)
        onViewCreated()
        //避免重复添加Fragment
        if (supportFragmentManager.fragments == null || supportFragmentManager.fragments.isEmpty()) {
            val firstFragment = getFirstFragment()
            if (firstFragment != null) {
                replaceFragment(firstFragment, false)
            }
        }
        setSwipeBackEnable(isSwipeBackEnable)      // 默认不带滑动退出的效果，让子类根据需要设置
        initialize(savedInstanceState)
        subscribeRxBus()
        onInitialized()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        LActivityUtils.removeActivity(this)
        super.onDestroy()
    }

    /**
     * 获取布局文件id
     * @return
     */
    protected abstract val layoutId: Int

    /**
     * 初始化操作
     * @param savedInstanceState
     */
    protected abstract fun initialize(savedInstanceState: Bundle?)

    /**
     * 处理Intent
     * @param intent
     */
    protected open fun handleIntent(intent: Intent) {}

    /**
     * 处理携带的数据
     * @param extras
     */
    protected open fun handleExtras(extras: Bundle) {}

    /**
     * 在setContentView之前
     */
    protected open fun onSetContentViewBefore() {}

    /**
     * 是否全屏显示布局，会将布局延伸到状态栏
     * @return
     */
    protected open val isFullScreenLayout: Boolean
        get() = false

    /**
     * 是否启动滑动返回
     * @return
     */
    protected open val isSwipeBackEnable: Boolean
        get() = false

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
            val transaction = supportFragmentManager.beginTransaction()
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
            val transaction = supportFragmentManager.beginTransaction()
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
            supportFragmentManager.beginTransaction()
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
            supportFragmentManager.beginTransaction()
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
            supportFragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit()
        }
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}
