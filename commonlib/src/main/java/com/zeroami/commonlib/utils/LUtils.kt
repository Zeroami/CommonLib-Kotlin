package com.zeroami.commonlib.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.media.RingtoneManager
import android.os.*
import android.view.View
import com.zeroami.commonlib.CommonLib
import java.io.Serializable

/**
 * 不好分类的工具方法
 *
 * @author Zeroami
 */
object LUtils {

    private val vibrator by lazy { CommonLib.ctx.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }

    /**
     * 复制文本到剪贴板
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

    /**
     * 生成Extras
     */
    fun generateExtras(vararg params: Pair<String, Any>): Bundle {
        val extras = Bundle()
        if (params.isNotEmpty()) {
            params.forEach {
                val value = it.second
                when (value) {
                    is Int -> extras.putInt(it.first, value)
                    is Long -> extras.putLong(it.first, value)
                    is CharSequence -> extras.putCharSequence(it.first, value)
                    is String -> extras.putString(it.first, value)
                    is Float -> extras.putFloat(it.first, value)
                    is Double -> extras.putDouble(it.first, value)
                    is Char -> extras.putChar(it.first, value)
                    is Short -> extras.putShort(it.first, value)
                    is Boolean -> extras.putBoolean(it.first, value)
                    is Serializable -> extras.putSerializable(it.first, value)
                    is Bundle -> extras.putBundle(it.first, value)
                    is Parcelable -> extras.putParcelable(it.first, value)
                    is Array<*> -> when {
                        value.isArrayOf<CharSequence>() -> extras.putCharSequenceArray(it.first, value as Array<out CharSequence>?)
                        value.isArrayOf<String>() -> extras.putStringArray(it.first, value as Array<out String>?)
                        value.isArrayOf<Parcelable>() -> extras.putParcelableArray(it.first, value as Array<out Parcelable>?)
                        else -> throw RuntimeException("extras ${it.first} has wrong type ${value.javaClass.name}")
                    }
                    is IntArray -> extras.putIntArray(it.first, value)
                    is LongArray -> extras.putLongArray(it.first, value)
                    is FloatArray -> extras.putFloatArray(it.first, value)
                    is DoubleArray -> extras.putDoubleArray(it.first, value)
                    is CharArray -> extras.putCharArray(it.first, value)
                    is ShortArray -> extras.putShortArray(it.first, value)
                    is BooleanArray -> extras.putBooleanArray(it.first, value)
                    else -> throw RuntimeException("extra ${it.first} has wrong type ${value.javaClass.name}")
                }
            }
        }
        return extras
    }

    @SuppressLint("MissingPermission")
    fun vibrate() {
        if (isAndroidO()) {
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(300, 300, 300), intArrayOf(VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE), -1))
        } else {
            vibrator.vibrate(longArrayOf(0, 300, 300, 300), -1)     // OFF/ON/OFF/ON
        }
    }

    fun playRing() {
        try {
            val ringtone = RingtoneManager.getRingtone(CommonLib.ctx, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            ringtone.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isAndroidO(): Boolean = Build.VERSION.SDK_INT >= 26
}
