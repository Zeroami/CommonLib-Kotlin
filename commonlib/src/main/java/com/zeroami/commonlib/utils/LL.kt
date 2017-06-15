package com.zeroami.commonlib.utils

import android.util.Log

import com.orhanobut.logger.Logger
import com.zeroami.commonlib.CommonLib

/**
 * 日志打印类

 * @author Zeroami
 */
object LL {


    fun d(message: String, vararg args: Any) {
        Logger.d(message, *args)
    }

    fun d(message: Any?) {
        Logger.d(message?.toString() ?: "null")
    }

    fun e(message: String, vararg args: Any) {
        Logger.e(message, *args)
    }

    fun e(throwable: Throwable) {
        Logger.e(throwable, null)
    }

    fun i(message: String, vararg args: Any) {
        Logger.i(message, *args)
    }

    fun v(message: String, vararg args: Any) {
        Logger.v(message, *args)
    }

    fun w(message: String, vararg args: Any) {
        Logger.w(message, *args)
    }

    fun wtf(message: String, vararg args: Any) {
        Logger.wtf(message, *args)
    }

    fun json(json: String) {
        Logger.json(json)
    }

    fun xml(xml: String) {
        Logger.xml(xml)
    }

    fun sd(message: String) {
        if (CommonLib.isDebug) {
            Log.d(CommonLib.TAG, message)
        }
    }

    fun sd(message: Any?) {
        if (CommonLib.isDebug) {
            Log.d(CommonLib.TAG, message?.toString() ?: "null")
        }
    }

    fun se(message: String) {
        if (CommonLib.isDebug) {
            Log.e(CommonLib.TAG, message)
        }
    }

    fun si(message: String) {
        if (CommonLib.isDebug) {
            Log.i(CommonLib.TAG, message)
        }
    }

    fun sv(message: String) {
        if (CommonLib.isDebug) {
            Log.v(CommonLib.TAG, message)
        }
    }

    fun sw(message: String) {
        if (CommonLib.isDebug) {
            Log.w(CommonLib.TAG, message)
        }
    }
}
