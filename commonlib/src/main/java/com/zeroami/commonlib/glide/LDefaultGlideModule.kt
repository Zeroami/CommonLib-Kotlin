package com.zeroami.commonlib.glide

import android.content.Context

import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.GlideModule
import com.zeroami.commonlib.utils.LFileUtils

/**
 * 默认的Glide配置
 *
 * @author Zeroami
 */
class LDefaultGlideModule : GlideModule {
    override fun applyOptions(context: Context, builder: GlideBuilder) {

        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)

        val calculator = MemorySizeCalculator(context)
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize

        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()

        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize))

        if (LFileUtils.checkSDCardAvailable()) {
            builder.setDiskCache(ExternalCacheDiskCacheFactory(context, "image_cache", 100 * 1024 * 1024))
        } else {
            builder.setDiskCache(InternalCacheDiskCacheFactory(context, "image_cache", 100 * 1024 * 1024))
        }


    }

    override fun registerComponents(context: Context, glide: Glide) {
        // nothing to do here
    }
}