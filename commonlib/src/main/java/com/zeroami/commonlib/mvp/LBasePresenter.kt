package com.zeroami.commonlib.mvp

import android.os.Bundle
import com.zeroami.commonlib.utils.LL
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * BasePresenter，实现MvpPresenter，完成Presenter的通用操作
 *
 * @author Zeroami
 */
abstract class LBasePresenter<V : LMvpView>(view: V) : LMvpPresenter<V>, LRxSupport {

    protected lateinit var mvpView: V
        private set

    private lateinit var emptyMvpView: V    // 一个空实现的MvpView，避免V和P解除绑定时P持有的V的MvpView引用为空导致空指针
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    init {
        attachView(view)
        createEmptyMvpView()
    }

    /**
     * 关联完成调用
     */
    protected open fun onViewAttached() {}

    /**
     * 解除关联完成调用
     */
    protected open fun onViewDetached() {}

    override fun doViewInitialized() {}

    override fun handleExtras(extras: Bundle) {}

    override fun subscribeEvent() {}

    override fun attachView(view: V) {
        mvpView = view
        onViewAttached()
    }

    override fun detachView() {
        mvpView = emptyMvpView
        compositeDisposable.clear()
        onViewDetached()
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    /**
     * 创建空实现的MvpView
     */
    @Suppress("UNCHECKED_CAST")
    private fun createEmptyMvpView() {
        emptyMvpView = Proxy.newProxyInstance(javaClass.classLoader, mvpView.javaClass.interfaces, object : InvocationHandler {
            @Throws(Throwable::class)
            override fun invoke(o: Any, method: Method, args: Array<Any>): Any? {
                LL.i("EmptyMvpView的%s方法被调用", method.name)
                if (method.declaringClass == Any::class.java) {
                    return method.invoke(this, *args)
                }
                return null
            }
        }) as V
    }

}
