package com.zeroami.commonlib

import android.content.Context
import android.text.TextUtils

import com.orhanobut.logger.LogLevel
import com.orhanobut.logger.Logger
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * CommonLib启动配置类
 *
 * @author Zeroami
 */
object CommonLib {

    var ctx: Context by object : ReadWriteProperty<Any?, Context> {
        private var value: Context? = null

        override fun getValue(thisRef: Any?, property: KProperty<*>): Context {
            return value ?: throw RuntimeException("请调用CommonLib.initialize(context)确保CommonLib正常工作")
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Context) {
            this.value = value
        }
    }

    val TAG = "TAG"
    var isDebug: Boolean = false

    /**
     * 初始化Context，使CommonLib正常工作
     * @param context
     */
    fun initialize(context: Context) {
        ctx = context
        setEnableLog(true)
    }

    /**
     * 日志开关
     * @param isEnableLog
     * @param tag
     */
    fun setEnableLog(isEnableLog: Boolean, tag: String = TAG) {
        if (isEnableLog) {
            Logger.init(tag).logLevel(LogLevel.FULL).methodCount(1).methodOffset(1)
        } else {
            Logger.init(tag).logLevel(LogLevel.NONE).methodCount(1).methodOffset(1)
        }
    }
}
