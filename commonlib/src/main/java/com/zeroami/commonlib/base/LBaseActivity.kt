package com.zeroami.commonlib.base


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.WindowManager
import com.zeroami.commonlib.library.swipebacklayout.SwipeBackActivity
import com.zeroami.commonlib.mvp.LRxSupport
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
        if (isHideStatusBar) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        if (isFullScreenLayout) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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
        subscribeEvent()
        onInitialized()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        LActivityUtils.removeActivity(this)
        super.onDestroy()
    }

    /**
     * 获取布局文件id
     */
    protected abstract val layoutId: Int

    /**
     * 初始化操作
     */
    protected abstract fun initialize(savedInstanceState: Bundle?)

    /**
     * 处理Intent
     */
    protected open fun handleIntent(intent: Intent) {}

    /**
     * 处理携带的数据
     */
    protected open fun handleExtras(extras: Bundle) {}

    /**
     * 在setContentView之前
     */
    protected open fun onSetContentViewBefore() {}

    /**
     * 是否全屏显示布局，会将布局延伸到状态栏
     */
    protected open val isFullScreenLayout: Boolean = false

    /**
     * 是否隐藏状态栏
     */
    protected open val isHideStatusBar: Boolean = false

    /**
     * 是否启动滑动返回
     */
    protected open val isSwipeBackEnable: Boolean = false

    /**
     * 获取Fragment容器id
     */
    protected open val fragmentContainerId: Int = 0

    /**
     * setContentView完成
     */
    protected open fun onViewCreated() {}

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
     */
    fun removeFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
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
