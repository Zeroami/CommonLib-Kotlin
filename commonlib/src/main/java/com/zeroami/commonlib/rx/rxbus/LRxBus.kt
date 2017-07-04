package com.zeroami.commonlib.rx.rxbus

import java.util.concurrent.ConcurrentHashMap

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * 基于RxJava实现的EventBus
 *
 * @author Zeroami
 */
object LRxBus {

    val NULL = LNull()
    val NULL_TYPE: Class<LNull> = LNull::class.java

    private val mSubject: Subject<Any> by lazy { PublishSubject.create<Any>().toSerialized() }
    private val mStickyEventMap: MutableMap<Class<*>, Any> by lazy { ConcurrentHashMap<Class<*>, Any>() }
    private val mStickyActionEventMap: MutableMap<String, Any> by lazy { ConcurrentHashMap<String, Any>() }

    fun post(event: Any) {
        mSubject.onNext(event)
    }

    fun post(event: Any, action: String) {
        mSubject.onNext(LRxBusEvent(action, event))
    }

    fun postAction(action: String) {
        mSubject.onNext(LRxBusEvent(action, NULL))
    }

    fun <T> subscribe(eventType: Class<T>, block: (T) -> Unit): Disposable {
        return mSubject.ofType(eventType).subscribe({
            try {
                block(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, {})
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> subscribe(eventType: Class<T>, action: String, block: (T) -> Unit): Disposable {
        return mSubject.filter({
            if (it !is LRxBusEvent) return@filter false
            val event = it
            return@filter eventType.isInstance(event.data) && action == event.action
        }).map({
            val event = it as LRxBusEvent
            return@map event.data as T
        }).subscribe({
            try {
                block(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, {})
    }

    fun subscribeAction(action: String, block: () -> Unit): Disposable {
        return subscribe(NULL_TYPE, action, { block() })
    }

    fun postSticky(event: Any) {
        synchronized(mStickyEventMap) {
            mStickyEventMap.put(event.javaClass, event)
        }
        post(event)
    }

    fun postSticky(event: Any, action: String) {
        synchronized(mStickyActionEventMap) {
            mStickyActionEventMap.put(event.javaClass.toString() + action, event)
        }
        post(event, action)
    }

    fun postStickyAction(action: String) {
        synchronized(mStickyActionEventMap) {
            mStickyActionEventMap.put(NULL.javaClass.toString() + action, NULL)
        }
        post(NULL, action)
    }

    fun <T> subscribeSticky(eventType: Class<T>, block: (T) -> Unit): Disposable {
        synchronized(mStickyEventMap) {
            val event = mStickyEventMap[eventType]
            if (event != null) {
                try {
                    block(eventType.cast(event))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return subscribe(eventType, block)
        }
    }

    fun <T> subscribeSticky(eventType: Class<T>, action: String, block: (T) -> Unit): Disposable {
        synchronized(mStickyActionEventMap) {
            val event = mStickyActionEventMap[eventType.toString() + action]
            if (event != null) {
                try {
                    block(eventType.cast(event))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return subscribe(eventType, action, block)
        }
    }

    fun subscribeStickyAction(action: String, block: () -> Unit): Disposable {
        return subscribeSticky(NULL_TYPE, action, { block() })
    }

    fun <T> getStickyEvent(eventType: Class<T>): T {
        synchronized(mStickyEventMap) {
            return eventType.cast(mStickyEventMap[eventType])
        }
    }

    fun <T> getStickyEvent(eventType: Class<T>, action: String): T {
        synchronized(mStickyActionEventMap) {
            return eventType.cast(mStickyActionEventMap[eventType.toString() + action])
        }
    }

    fun <T> removeStickyEvent(eventType: Class<T>): T {
        synchronized(mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType))
        }
    }

    fun <T> removeStickyEvent(eventType: Class<T>, action: String): T {
        synchronized(mStickyActionEventMap) {
            return eventType.cast(mStickyActionEventMap.remove(eventType.toString() + action))
        }
    }

    fun removeAllStickyEvents() {
        synchronized(mStickyEventMap) {
            mStickyEventMap.clear()
        }
        synchronized(mStickyActionEventMap) {
            mStickyActionEventMap.clear()
        }
    }

    fun hasObservers(): Boolean {
        return mSubject.hasObservers()
    }
}
