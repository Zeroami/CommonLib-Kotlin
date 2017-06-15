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
     * @param context
     * @param interval 时间间隔，单位为秒
     * @param cls
     * @param action
     * @param isWakeup
     */
    @JvmOverloads fun startPollingService(context: Context, interval: Int,
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
                (interval * 1000).toLong(), pendingIntent)
    }

    /**
     * 停止轮询服务
     * @param context
     * @param cls
     * @param action
     */
    @JvmOverloads fun stopPollingService(context: Context, cls: Class<*>,
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