package com.zeroami.commonlib.utils

import android.content.Context
import android.content.SharedPreferences

import com.zeroami.commonlib.CommonLib

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * SharedPreferences封装类
 *
 * @author Zeroami
 */
object LSPUtils {

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author Zeroami
     */
    private object LSharedPreferencesCompat {
        private val applyMethod = findApplyMethod()

        /**
         * 反射查找apply的方法
         * @return
         */
        private fun findApplyMethod(): Method? {
            try {
                val clz = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {
            }

            return null
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         * @param editor
         */
        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (applyMethod != null) {
                    applyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalArgumentException) {
            } catch (e: IllegalAccessException) {
            } catch (e: InvocationTargetException) {
            }

            editor.commit()
        }
    }

    /**
     * 保存在手机里面的文件名
     */
    private var fileName = "share_data"

    fun initialize(fileName: String) {
        this.fileName = fileName
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param key
     * @param value
     */
    fun put(key: String, value: Any) {

        val sp = CommonLib.ctx.getSharedPreferences(fileName,
                Context.MODE_PRIVATE)
        val editor = sp.edit()

        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> editor.putString(key, value.toString())
        }
        LSharedPreferencesCompat.apply(editor)
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param key
     * @param defaultValue
     * @param <T>
     * @return
     */
    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    fun <T> get(key: String, defaultValue: T): T {
        val sp = CommonLib.ctx.getSharedPreferences(fileName,
                Context.MODE_PRIVATE)

        return when (defaultValue) {
            is String -> sp.getString(key, defaultValue)
            is Int -> sp.getInt(key, defaultValue)
            is Boolean -> sp.getBoolean(key, defaultValue)
            is Float -> sp.getFloat(key, defaultValue)
            is Long -> sp.getLong(key, defaultValue)
            else -> defaultValue
        } as T
    }

    /**
     * 移除某个key值已经对应的值
     * @param key
     */
    fun remove(key: String) {
        val sp = CommonLib.ctx.getSharedPreferences(fileName,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        LSharedPreferencesCompat.apply(editor)
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        val sp = CommonLib.ctx.getSharedPreferences(fileName,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear()
        LSharedPreferencesCompat.apply(editor)
    }

    /**
     * 查询某个key是否已经存在
     * @param key
     * @return
     */
    operator fun contains(key: String): Boolean {
        val sp = CommonLib.ctx.getSharedPreferences(fileName,
                Context.MODE_PRIVATE)
        return sp.contains(key)
    }

    /**
     * 返回所有的键值对
     * @return
     */
    val all: Map<String, *>
        get() {
            val sp = CommonLib.ctx.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE)
            return sp.all
        }

}