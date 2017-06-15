package com.zeroami.commonlib.utils


import android.graphics.drawable.Drawable

import com.zeroami.commonlib.CommonLib

/**
 * R资源工具类
 *
 * @author Zeroami
 */
object LRUtils {

    val POINT = "."
    val R = "R"
    val JOIN = "$"
    val ANIM = "anim"
    val ATTR = "attr"
    val COLOR = "color"
    val DIMEN = "dimen"
    val DRAWABLE = "drawable"
    val MIPMAP = "mipmap"
    val ID = "id"
    val LAYOUT = "layout"
    val MENU = "menu"
    val RAW = "raw"
    val STRING = "string"
    val STYLE = "style"
    val STYLEABLE = "styleable"

    /**
     * 获取资源Id内容
     * @param resId
     * @return
     */
    fun getString(resId: Int, vararg formatArgs: Any): String {
        return CommonLib.ctx.resources.getString(resId, formatArgs)
    }

    /**
     * 获取资源Id内容
     * @param resId
     * @return
     */
    fun getColor(resId: Int): Int {
        return CommonLib.ctx.resources.getColor(resId)
    }

    /**
     * 获取资源Id内容
     * @param resId
     * @return
     */
    fun getDimension(resId: Int): Float {
        return CommonLib.ctx.resources.getDimension(resId)
    }

    /**
     * 获取资源Id内容
     * @param resId
     * @return
     */
    fun getDimensionPixelSize(resId: Int): Int {
        return CommonLib.ctx.resources.getDimensionPixelSize(resId)
    }

    /**
     * 获取资源Id内容
     * @param resId
     * @return
     */
    fun getDrawable(resId: Int): Drawable {
        return CommonLib.ctx.resources.getDrawable(resId)
    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getAnimId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + ANIM)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getAttrId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + ATTR)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getColorId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + COLOR)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getDimenId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + DIMEN)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getDrawableId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + DRAWABLE)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getMipmapId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + MIPMAP)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + ID)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getLayoutId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + LAYOUT)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getMenuId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + MENU)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getRawId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + RAW)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getStringId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + STRING)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getStyleId(name: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + STYLE)
                    .getDeclaredField(name)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 根据字符串获取Id
     * @param name
     * @return
     */
    fun getStyleable(name: String): IntArray? {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + STYLEABLE)
                    .getDeclaredField(name)
                    .get(null) as IntArray
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 根据字符串获取Id
     * @param styleableName
     * @param attributeName
     * @return
     */
    fun getStyleableAttribute(
            styleableName: String, attributeName: String): Int {
        try {
            return Class
                    .forName(CommonLib.ctx.packageName + POINT + R + JOIN + STYLEABLE)
                    .getDeclaredField(styleableName + "_" + attributeName)
                    .get(null) as Int
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }
}
