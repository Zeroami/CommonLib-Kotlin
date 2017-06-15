package com.zeroami.commonlib.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.view.View

import com.google.gson.internal.`$Gson$Types`
import com.zeroami.commonlib.CommonLib

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 不好分类的工具方法
 *
 * @author Zeroami
 */
object LUtils {

    /**
     * 复制文本到剪贴板
     * @param text
     */
    fun copyToClipboard(text: String) {
        if (Build.VERSION.SDK_INT >= 11) {
            val cbm = CommonLib.ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cbm.primaryClip = ClipData.newPlainText(CommonLib.ctx.packageName, text)
        } else {
            val cbm = CommonLib.ctx.getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
            cbm.text = text
        }
    }

    /**
     * 判断事件点是否在控件内
     * @param view
     * @param x
     * @param y
     * @return
     */
    fun isTouchPointInView(view: View, x: Float, y: Float): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val left = location[0]
        val top = location[1]
        val right = left + view.measuredWidth
        val bottom = top + view.measuredHeight
        if (y in top..bottom && x in left..right) {
            return true
        }
        return false
    }
}
