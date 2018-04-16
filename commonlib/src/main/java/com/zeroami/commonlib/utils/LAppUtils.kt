package com.zeroami.commonlib.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import com.zeroami.commonlib.CommonLib

/**
 * 描述：App工具类，获取App相关信息
 *
 * @author Zeroami
 */
object LAppUtils {


    /**
     * 获取版本号
     */
    val versionCode: Int
        get() {
            var versionCode = 0
            try {
                val packageName = CommonLib.ctx.packageName
                versionCode = CommonLib.ctx.packageManager.getPackageInfo(packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return versionCode
        }

    /**
     * 获取版本字符串
     */
    val versionName: String
        get() {
            var versionName = ""
            try {
                val packageName = CommonLib.ctx.packageName
                versionName = CommonLib.ctx.packageManager.getPackageInfo(packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return versionName
        }

    /**
     * 检测服务是否运行
     */
    fun isServiceRunning(className: String): Boolean {
        val services = (CommonLib.ctx
                .getSystemService(Context.ACTIVITY_SERVICE)
                as ActivityManager)
                .getRunningServices(Integer.MAX_VALUE)
        return services.any { it.service.className == className }
    }

    /**
     * 判断应用是否处于后台状态
     */
    fun isBackground(packageName: String = CommonLib.ctx.packageName): Boolean {
        val tasks = (CommonLib.ctx
                .getSystemService(Context.ACTIVITY_SERVICE)
                as ActivityManager).getRunningTasks(1)
        return tasks.none { it.topActivity.packageName == packageName }
    }

    /**
     * 判断App进程是否存活
     */
    fun isAppAlive(packageName: String = CommonLib.ctx.packageName): Boolean {
        val processes = (CommonLib.ctx
                .getSystemService(Context.ACTIVITY_SERVICE)
                as ActivityManager).runningAppProcesses
        return processes.any { it.processName == packageName }
    }

    /**
     * 获取手机系统SDK版本
     */
    val sdkVersion: Int
        get() = android.os.Build.VERSION.SDK_INT

}
