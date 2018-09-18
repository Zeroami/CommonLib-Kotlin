package com.zeroami.commonlib.utils

import android.util.Log

import com.orhanobut.logger.Logger
import com.zeroami.commonlib.CommonLib

/**
 * 日志打印类

 * @author Zeroami
 */
object LL {

    private var isEnableLog = true
    private var tag = CommonLib.TAG

    fun setEnableLog(isEnableLog: Boolean, tag: String = CommonLib.TAG) {
        this.isEnableLog = isEnableLog
        this.tag = tag
    }

    @JvmStatic
    fun d(message: String, vararg args: Any) {
        Logger.d(message, *args)
    }

    @JvmStatic
    fun d(message: Any?) {
        Logger.d(message?.toString() ?: "null")
    }

    @JvmStatic
    fun e(message: String, vararg args: Any) {
        Logger.e(message, *args)
    }

    @JvmStatic
    fun e(throwable: Throwable) {
        Logger.e(throwable, null)
    }

    @JvmStatic
    fun i(message: String, vararg args: Any) {
        Logger.i(message, *args)
    }

    @JvmStatic
    fun v(message: String, vararg args: Any) {
        Logger.v(message, *args)
    }

    @JvmStatic
    fun w(message: String, vararg args: Any) {
        Logger.w(message, *args)
    }

    @JvmStatic
    fun wtf(message: String, vararg args: Any) {
        Logger.wtf(message, *args)
    }

    @JvmStatic
    fun json(json: String) {
        Logger.json(json)
    }

    @JvmStatic
    fun xml(xml: String) {
        Logger.xml(xml)
    }

    @JvmStatic
    fun sd(message: String) {
        if (isEnableLog) {
            Log.d(tag, message)
        }
    }

    @JvmStatic
    fun sd(message: Any?) {
        if (isEnableLog) {
            Log.d(tag, message?.toString() ?: "null")
        }
    }

    @JvmStatic
    fun se(message: String) {
        if (isEnableLog) {
            Log.e(tag, message)
        }
    }

    @JvmStatic
    fun si(message: String) {
        if (isEnableLog) {
            Log.i(tag, message)
        }
    }

    @JvmStatic
    fun sv(message: String) {
        if (isEnableLog) {
            Log.v(tag, message)
        }
    }

    @JvmStatic
    fun sw(message: String) {
        if (isEnableLog) {
            Log.w(tag, message)
        }
    }
}