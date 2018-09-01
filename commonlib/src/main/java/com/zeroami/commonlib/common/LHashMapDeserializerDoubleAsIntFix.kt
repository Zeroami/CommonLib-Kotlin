package com.zeroami.commonlib.common

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

/**
 * 解决Gson转换中int自动转为double的问题
 *
 * @author Zeroami
 */
class LHashMapDeserializerDoubleAsIntFix : JsonDeserializer<HashMap<String, Any>> {

    @Throws(JsonParseException::class)
    override fun deserialize(jsonElement: JsonElement, type: Type, jsonDeserializationContext: JsonDeserializationContext): HashMap<String, Any>? {
        return read(jsonElement) as HashMap<String, Any>?
    }

    fun read(`in`: JsonElement): Any? {
        if (`in`.isJsonArray) {
            val list = ArrayList<Any>()
            val arr = `in`.asJsonArray
            for (anArr in arr) {
                read(anArr)?.let {
                    list.add(it)
                }
            }
            return list
        } else if (`in`.isJsonObject) {
            val map = HashMap<String, Any>()
            val obj = `in`.asJsonObject
            val entitySet = obj.entrySet()
            for ((key, value) in entitySet) {
                read(value)?.let {
                    map.put(key, it)
                }
            }
            return map
        } else if (`in`.isJsonPrimitive) {
            val prim = `in`.asJsonPrimitive
            if (prim.isBoolean) {
                return prim.asBoolean
            } else if (prim.isString) {
                return prim.asString
            } else if (prim.isNumber) {
                val num = prim.asNumber
                // here you can handle double int/long values
                // and return any type you want
                // this solution will transform 3.0 float to long values
                return if (Math.ceil(num.toDouble()) == num.toLong().toDouble())
                    num.toLong()
                else {
                    num.toDouble()
                }
            }
        }
        return null
    }
}