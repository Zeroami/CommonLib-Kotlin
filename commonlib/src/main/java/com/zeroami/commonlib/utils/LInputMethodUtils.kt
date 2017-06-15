package com.zeroami.commonlib.utils

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager

import com.zeroami.commonlib.CommonLib

/**
 * 输入法工具类
 *
 * @author Zeroami
 */
object LInputMethodUtils {

    /**
     * 显示输入法
     * @param view
     */
    fun showInputMethod(view: View) {
        (CommonLib.ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 隐藏输入法
     * @param view
     */
    fun hideInputMethod(view: View) {
        val imm = CommonLib.ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 点击空白隐藏输入法
     * @param activity
     * @param onTouchListener
     */
    fun hideInputMethodOnTouchSpace(activity: Activity, onTouchListener: View.OnTouchListener? = null) {
        activity.window.decorView.setOnTouchListener(View.OnTouchListener { v, event ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (activity.currentFocus != null) {
                imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            } else {
                imm.hideSoftInputFromWindow(activity.findViewById(android.R.id.content).windowToken, 0)
            }
            if (onTouchListener != null) {
                return@OnTouchListener onTouchListener.onTouch(v, event)
            }
            false
        })
    }
}
