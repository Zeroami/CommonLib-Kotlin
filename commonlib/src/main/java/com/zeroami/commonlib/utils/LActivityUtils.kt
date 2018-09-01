package com.zeroami.commonlib.utils

import android.app.Activity
import org.jetbrains.anko.collections.forEachReversed
import java.util.*

/**
 * Activity工具类
 *
 * @author Zeroami
 */
object LActivityUtils {

    val activityList by lazy { ArrayList<Activity>() }

    /**
     * 将Activity加入Activity列表
     */
    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    /**
     * 将Activity移出Activity列表
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
     */
    val topActivity: Activity?
        get() {
            return activityList.lastOrNull()
        }

    /**
     * 获取栈顶第一个有效的Activity
     */
    val topActiveActivity: Activity?
        get() {
            activityList.forEachReversed {
                if (!it.isFinishing) {
                    return it
                }
            }
            return null
        }
}