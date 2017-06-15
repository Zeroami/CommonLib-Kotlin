package com.zeroami.commonlib.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager

import com.zeroami.commonlib.CommonLib

/**
 * 网络工具类，获取网络连接状态
 *
 * @author Zeroami
 */
class LNetUtils private constructor() {
    private val receiver: LNetworkBroadcastReceiver
    private val filter: IntentFilter
    private var listener: (Boolean) -> Unit = {}

    init {
        receiver = LNetworkBroadcastReceiver()
        filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
    }

    /**
     * 注册监听器
     * @param listener
     */
    fun registerNetworkStateListener(listener:(Boolean) -> Unit) {
        CommonLib.ctx.registerReceiver(receiver, filter)
        this.listener = listener
    }

    /**
     * 反注册监听器，释放对象
     */
    fun unregisterNetworkStateListener() {
        CommonLib.ctx.unregisterReceiver(receiver)
        listener = {}
    }

    /**
     * 网络状态广播接收者
     *
     * @author Zeroami
     */
    private inner class LNetworkBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            listener(LNetUtils.isNetworkConnected)
        }
    }

    private object Holder {
        val INSTANCE = LNetUtils()
    }

    companion object {

        val instance by lazy { Holder.INSTANCE }

        /**
         * 判断是否有网络连接
         * @return
         */
        val isNetworkConnected: Boolean
            get() {
                val networkInfo = (CommonLib.ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
                if (networkInfo != null)
                    return networkInfo.isAvailable && networkInfo.isConnected
                return false
            }


        /**
         * 判断WIFI连接是否可用
         * @return
         */
        val isWifiConnected: Boolean
            get() {
                val networkInfo = (CommonLib.ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
                if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI)
                    return networkInfo.isAvailable && networkInfo.isConnected
                return false
            }

        /**
         * 判断MOBILE网络是否可用
         * @return
         */
        val isMobileConnected: Boolean
            get() {
                val networkInfo = (CommonLib.ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
                if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE)
                    return networkInfo.isAvailable && networkInfo.isConnected
                return false
            }
    }
}
