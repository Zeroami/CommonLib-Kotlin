package com.zeroami.commonlib.mvp

import android.os.Bundle
import com.zeroami.commonlib.utils.LL
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * BasePresenter，实现MvpPresenter，完成Presenter的通用操作
 *
 * @author Zeroami
 */
abstract class LBasePresenter<V : LMvpView, M : LMvpModel>(view: V) : LMvpPresenter<V>, LRxSupport {

    protected lateinit var mvpView: V
        private set

    private lateinit var emptyMvpView: V    // 一个空实现的MvpView，避免V和P解除绑定时P持有的V的MvpView引用为空导致空指针

    /**
     * 获取MvpModel
     * @return
     */
    protected val mvpModel: M by object : ReadOnlyProperty<Any?, M> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): M {
            return model ?: throw NullPointerException()
        }

    }

    private var model: M? = null

    private val realModel: M?
    private val testModel: M?
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    init {
        attachView(view)
        createEmptyMvpView()
        realModel = createRealModel()
        testModel = createTestModel()
        model = realModel
        subscribeRxBus()
    }

    /**
     * 订阅RxBus
     */
    protected open fun subscribeRxBus() {}

    /**
     * 关联完成调用
     */
    protected open fun onViewAttached() {}

    /**
     * 解除关联完成调用
     */
    protected open fun onViewDetached() {}

    /**
     * 创建真实的数据Model
     * @return
     */
    protected abstract fun createRealModel(): M?

    /**
     * 创建测试的数据Model
     * @return
     */
    protected open fun createTestModel(): M? = null

    override fun doViewInitialized() {}

    override fun doHandleExtras(extras: Bundle) {}

    override fun attachView(view: V) {
        mvpView = view
        onViewAttached()
    }

    override fun detachView() {
        mvpView = emptyMvpView
        compositeDisposable.clear()
        onViewDetached()
    }

    /**
     * 切换数据仓库
     * @param isTest
     */
    protected fun switchModel(isTest: Boolean) {
        if (isTest) {
            model = testModel
        } else {
            model = realModel
        }
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
