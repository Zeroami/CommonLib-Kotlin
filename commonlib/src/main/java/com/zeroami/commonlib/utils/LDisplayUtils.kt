package com.zeroami.commonlib.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager

import com.zeroami.commonlib.CommonLib

/**
 * 系统显示相关工具类
 *
 * @author Zeroami
 */
object LDisplayUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param dpValue
     * @return
     */
    fun dip2px(dpValue: Float): Int {
        val scale = CommonLib.ctx.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param pxValue
     * @return
     */
    fun px2dip(pxValue: Float): Int {
        val scale = CommonLib.ctx.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     * @param pxValue
     * @return
     */
    fun px2sp(pxValue: Float): Int {
        val fontScale = CommonLib.ctx.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     * @param spValue
     * @return
     */
    fun sp2px(spValue: Float): Int {
        val fontScale = CommonLib.ctx.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    val screenWidth: Int
        get() {
            val dm = DisplayMetrics()
            (CommonLib.ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(dm)
            val w = dm.widthPixels
            return w
        }

    /**
     * 获取屏幕高度
     * @return
     */
    val screenHeight: Int
        get() {
            val dm = DisplayMetrics()
            (CommonLib.ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(dm)
            val h = dm.heightPixels
            return h
        }

    /**
     * 获得状态栏的高度
     * @return
     */
    val statusHeight: Int
        get() {
            var statusHeight = -1
            try {
                val clazz = Class.forName("com.android.internal.R\$dimen")
                val obj = clazz.newInstance()
                val heightId = Integer.parseInt(clazz.getField("status_bar_height")
                        .get(obj).toString())
                statusHeight = CommonLib.ctx.resources.getDimensionPixelSize(heightId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return statusHeight
        }

    /**
     * 获取View的测量宽高
     * @param view
     * @param callback
     */
    fun getViewMeasureSize(view: View, callback: (with: Int, height: Int) -> Unit) {
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                callback(view.measuredWidth, view.measuredHeight)
            }
        })
    }
}
