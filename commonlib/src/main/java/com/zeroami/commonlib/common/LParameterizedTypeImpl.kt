package com.zeroami.commonlib.common

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 自定义ParameterizedType，方便Gson转化，例：LParameterizedTypeImpl(Map::class.java, arrayOf(String::class.java, String::class.java)
 *
 * @author Zeroami
 */
class LParameterizedTypeImpl(private val raw: Class<*>, args: Array<Type>? = null) : ParameterizedType {

    private val args: Array<Type> = args ?: arrayOf()

    override fun getActualTypeArguments(): Array<Type> {
        return args
    }

    override fun getRawType(): Type {
        return raw
    }

    override fun getOwnerType(): Type? {
        return null
    }
}