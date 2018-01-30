package com.zeroami.commonlib.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.zeroami.commonlib.CommonLib
import java.io.File

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
     * 安装Apk
     */
    fun installApk(file: File) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive")
        CommonLib.ctx.startActivity(intent)
    }

    /**
     * 安装Apk
     */
    fun installApk(uri: Uri) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        CommonLib.ctx.startActivity(intent)
    }

    /**
     * 卸载apk
     */
    fun uninstallApk(packageName: String) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val packageURI = Uri.parse("package:" + packageName)
        intent.data = packageURI
        CommonLib.ctx.startActivity(intent)
    }

    /**
     * 检测服务是否运行
     */
    fun isServiceRunning(className: String): Boolean {
        val servicesList = (CommonLib.ctx
                .getSystemService(Context.ACTIVITY_SERVICE)
                as ActivityManager)
                .getRunningServices(Integer.MAX_VALUE)
        val isRunning = servicesList.any { className == it.service.className }
        return isRunning
    }

    /**
     * 判断应用是否处于后台状态
     */
    val isBackground: Boolean
        get() {
            val tasks = (CommonLib.ctx
                    .getSystemService(Context.ACTIVITY_SERVICE)
                    as ActivityManager).getRunningTasks(1)
            if (!tasks.isEmpty()) {
                val topActivity = tasks[0].topActivity
                if (topActivity.packageName != CommonLib.ctx.packageName) {
                    return true
                }
            }
            return false
        }


    /**
     * 获取手机系统SDK版本
     */
    val sdkVersion: Int
        get() = android.os.Build.VERSION.SDK_INT

}
