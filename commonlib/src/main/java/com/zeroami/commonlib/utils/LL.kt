package com.zeroami.commonlib.utils

import android.util.Log

import com.orhanobut.logger.Logger
import com.zeroami.commonlib.CommonLib

/**
 * 日志打印类

 * @author Zeroami
 */
object LL {


    @JvmStatic fun d(message: String, vararg args: Any) {
        Logger.d(message, *args)
    }

    @JvmStatic fun d(message: Any?) {
        Logger.d(message?.toString() ?: "null")
    }

    @JvmStatic fun e(message: String, vararg args: Any) {
        Logger.e(message, *args)
    }

    @JvmStatic fun e(throwable: Throwable) {
        Logger.e(throwable, null)
    }

    @JvmStatic fun i(message: String, vararg args: Any) {
        Logger.i(message, *args)
    }

    @JvmStatic fun v(message: String, vararg args: Any) {
        Logger.v(message, *args)
    }

    @JvmStatic fun w(message: String, vararg args: Any) {
        Logger.w(message, *args)
    }

    @JvmStatic fun wtf(message: String, vararg args: Any) {
        Logger.wtf(message, *args)
    }

    @JvmStatic fun json(json: String) {
        Logger.json(json)
    }

    @JvmStatic fun xml(xml: String) {
        Logger.xml(xml)
    }

    @JvmStatic fun sd(message: String) {
        if (CommonLib.isDebug) {
            Log.d(CommonLib.TAG, message)
        }
    }

    @JvmStatic fun sd(message: Any?) {
        if (CommonLib.isDebug) {
            Log.d(CommonLib.TAG, message?.toString() ?: "null")
        }
    }

    @JvmStatic fun se(message: String) {
        if (CommonLib.isDebug) {
            Log.e(CommonLib.TAG, message)
        }
    }

    @JvmStatic fun si(message: String) {
        if (CommonLib.isDebug) {
            Log.i(CommonLib.TAG, message)
        }
    }

    @JvmStatic fun sv(message: String) {
        if (CommonLib.isDebug) {
            Log.v(CommonLib.TAG, message)
        }
    }

    @JvmStatic fun sw(message: String) {
        if (CommonLib.isDebug) {
            Log.w(CommonLib.TAG, message)
        }
    }
}
