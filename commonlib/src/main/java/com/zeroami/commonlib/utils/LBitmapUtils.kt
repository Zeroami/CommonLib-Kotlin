package com.zeroami.commonlib.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View

import com.zeroami.commonlib.CommonLib

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

/**
 * Bitmap工具类
 *
 * @author Zeroami
 */
object LBitmapUtils {

    /**
     * 图片压缩处理（使用Options的方法）
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): BitmapFactory.Options {
        // 源图片的高度和宽度
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高一定都会大于等于目标的宽和高。
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        // 设置压缩比例
        options.inSampleSize = inSampleSize
        return options
    }

    /**
     * 从资源中获取Bitmap
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun getBitmapFromResource(resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(CommonLib.ctx.resources, resId, options)
        options = calculateInSampleSize(options, reqWidth,
                reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(CommonLib.ctx.resources, resId, options)
    }

    /**
     * 从文件中获取Bitmap
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun getBitmapFromFile(path: String, reqWidth: Int, reqHeight: Int): Bitmap {
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        options = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, options)
    }

    /**
     * 从数组中获取Bitmap
     * @param data
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun getBitmapFromByteArray(data: ByteArray, reqWidth: Int, reqHeight: Int): Bitmap {
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(data, 0, data.size, options)
        options = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(data, 0, data.size, options)
    }

    /**
     * 把bitmap转化为bytes
     * @param bitmap
     * @return
     */
    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }

    /**
     * 从View获取Bitmap
     * @param view view
     * @return
     */
    fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(canvas)
        return bitmap
    }

    /**
     * 将Drawable转化为Bitmap
     * @param drawable
     * @return
     */
    fun getBitmapFromDrawable(drawable: Drawable): Bitmap {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val config = if (drawable.opacity != PixelFormat.OPAQUE)
            Bitmap.Config.ARGB_8888
        else
            Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(width, height, config)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return bitmap
    }


    /**
     * 获取圆角Bitmap
     * @param bitmap
     * @param radius
     * @return
     */
    fun getRoundBitmap(bitmap: Bitmap, radius: Float): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width,
                bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        canvas.drawRoundRect(rectF, radius, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    /**
     * 获取圆形Bitmap
     * @param bitmap
     * @return
     */
    fun getCircleBitmap(bitmap: Bitmap): Bitmap {
        val width = Math.min(bitmap.width, bitmap.height)
        val output = Bitmap.createBitmap(width,
                width, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        canvas.drawCircle(width * 1.0f / 2, width * 1.0f / 2, width * 1.0f / 2, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, -(bitmap.width - width) * 1.0f / 2, -(bitmap.height - width) * 1.0f / 2, paint)
        return output
    }

    /**
     * 压缩图片大小
     * @param bitmap
     * @param maxKb     压缩的图片大小的最大值
     * @return
     */
    fun compressImage(bitmap: Bitmap, maxKb: Int): Bitmap {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var options = 100
        while (baos.toByteArray().size / 1024 > maxKb) { // 循环判断如果压缩后图片是否大于多少kb,大于继续压缩
            baos.reset()// 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10// 每次都减少10
        }
        val bais = ByteArrayInputStream(baos.toByteArray())// 把压缩后的数据baos存放到ByteArrayInputStream中
        val output = BitmapFactory.decodeStream(bais, null, null)// 把ByteArrayInputStream数据生成图片
        return output
    }

    /**
     * 将彩色图转换为灰度图
     * @param bitmap
     * @return
     */
    fun convertGrayBitmap(bitmap: Bitmap): Bitmap {
        val width = bitmap.width // 获取位图的宽
        val height = bitmap.height // 获取位图的高

        val pixels = IntArray(width * height) // 通过位图的大小创建像素点数组

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        val alpha = 0xFF shl 24
        for (i in 0..height - 1) {
            for (j in 0..width - 1) {
                var grey = pixels[width * i + j]

                val red = grey and 0x00FF0000 shr 16
                val green = grey and 0x0000FF00 shr 8
                val blue = grey and 0x000000FF

                grey = (red.toFloat() * 0.3 + green.toFloat() * 0.59 + blue.toFloat() * 0.11).toInt()
                grey = alpha or (grey shl 16) or (grey shl 8) or grey
                pixels[width * i + j] = grey
            }
        }
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        result.setPixels(pixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * 保存图片到本地
     * @param bitmap
     * @param dir
     * @param filename
     * @param isScan 是否插入系统媒体库
     * @return
     */
    fun saveImage(bitmap: Bitmap, dir: String, filename: String, isScan: Boolean): Boolean {
        val path = File(dir)
        if (!path.exists()) {
            path.mkdirs()
        }
        val file = File(path.toString() + "/" + filename)
        if (file.exists()) {
            file.delete()
        }
        try {
            file.createNewFile()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                    fileOutputStream)
            fileOutputStream.flush()

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        // 其次把文件插入到系统图库
        if (isScan) {
            try {
                MediaStore.Images.Media.insertImage(CommonLib.ctx.contentResolver,
                        file.absolutePath, filename, null)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            // 最后通知图库更新
            CommonLib.ctx.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)))
        }

        return true
    }

    /**
     * 获取当前屏幕截图，包含状态栏，但是获取不到状态栏内容
     * @param activity
     * @return
     */
    fun takeScreenShot(activity: Activity): Bitmap {
        //通过window的源码可以看出：检索顶层窗口的装饰视图，可以作为一个窗口添加到窗口管理器
        val view = activity.window.decorView
        //启用或禁用绘图缓存
        view.isDrawingCacheEnabled = true
        //创建绘图缓存
        view.buildDrawingCache()
        //拿到绘图缓存
        val bitmap = view.drawingCache

        val width = LDisplayUtils.screenWidth
        val height = LDisplayUtils.screenHeight

        val output = Bitmap.createBitmap(bitmap, 0, 0, width, height)
        view.destroyDrawingCache()
        return output
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     * @param activity
     * @return
     */
    fun takeScreenShotWithoutStatusBar(activity: Activity): Bitmap {
        //通过window的源码可以看出：检索顶层窗口的装饰视图，可以作为一个窗口添加到窗口管理器
        val view = activity.window.decorView
        //启用或禁用绘图缓存
        view.isDrawingCacheEnabled = true
        //创建绘图缓存
        view.buildDrawingCache()
        //拿到绘图缓存
        val bitmap = view.drawingCache

        //状态栏高度
        val statusBarHeight = LDisplayUtils.statusHeight
        val width = LDisplayUtils.screenWidth
        val height = LDisplayUtils.screenHeight

        val output = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return output
    }
}
