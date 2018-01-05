package com.zeroami.commonlib.utils

import android.app.Activity

import java.util.ArrayList

/**
 * Activity工具类
 *
 * @author Zeroami
 */
object LActivityUtils {

    val activityList by lazy { ArrayList<Activity>() }

    /**
     * 将Activity加入Activity列表
     * @param activity
     */
    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    /**
     * 将Activity移出Activity列表
     * @param activity
     */
    fun removeActivity(activity: Activity) {
        activityList.remove(activity)
    }

    /**
     * 退出所有Activity
     */
    fun finishAllActivity() {
        activityList.forEach { if (!it.isFinishing) it.finish() }
    }

    /**
     * 获取栈顶Activity
     * @return
     */
    val topActivity: Activity?
        get() {
            if (activityList.size > 0) {
                return activityList[activityList.size - 1]
            }
            return null
        }
}
