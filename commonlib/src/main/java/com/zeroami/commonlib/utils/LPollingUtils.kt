package com.zeroami.commonlib.utils


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.text.TextUtils

/**
 * 轮询服务工具类
 *
 * @author Zeroami
 */
object LPollingUtils {

    /**
     * 开启轮询服务
     */
    @JvmOverloads
    fun startPollingService(context: Context, intervalMillis: Long,
                            cls: Class<*>, action: String? = null, isWakeup: Boolean = true) {
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, cls)
        if (!TextUtils.isEmpty(action)) {
            intent.action = action
        }
        val pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val triggerAtTime = System.currentTimeMillis()
        val type = if (isWakeup) AlarmManager.RTC_WAKEUP else AlarmManager.RTC
        manager.setRepeating(type, triggerAtTime,
                intervalMillis, pendingIntent)
    }

    /**
     * 停止轮询服务
     */
    @JvmOverloads
    fun stopPollingService(context: Context, cls: Class<*>,
                           action: String? = null) {
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, cls)
        if (!TextUtils.isEmpty(action)) {
            intent.action = action
        }
        val pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        manager.cancel(pendingIntent)
    }
}