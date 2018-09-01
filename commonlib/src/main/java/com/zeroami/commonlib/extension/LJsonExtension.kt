package com.zeroami.commonlib.extension

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.zeroami.commonlib.common.LHashMapDeserializerDoubleAsIntFix
import com.zeroami.commonlib.common.LMapDeserializerDoubleAsIntFix
import org.json.JSONArray
import org.json.JSONObject

val GSON by lazy {
    GsonBuilder()
            .registerTypeAdapter(object : TypeToken<HashMap<String, Any>>() {}.type, LHashMapDeserializerDoubleAsIntFix())
            .registerTypeAdapter(object : TypeToken<Map<String, Any>>() {}.type, LMapDeserializerDoubleAsIntFix())
            .create()
}

inline fun <reified T> jsonToObject(json: String): T {
    return GSON.fromJson(json, object : TypeToken<T>() {}.type)
}

fun objectToJson(obj: Any): String {
    return GSON.toJson(obj)
}

inline fun <reified T> jsonConvert(obj: Any): T {
    if (obj is String || obj is JSONObject || obj is JSONArray) {
        return jsonToObject(obj.toString())
    }
    return jsonToObject(objectToJson(obj))
}

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
fun <T> JSONObject.get(name: String, defaultValue: T): T {
    return try {
        when (defaultValue) {
            is Boolean -> getBoolean(name)
            is Double -> getDouble(name)
            is Int -> getInt(name)
            is Long -> getLong(name)
            is String -> getString(name)
            is JSONArray -> getJSONArray(name)
            is JSONObject -> getJSONObject(name)
            else -> defaultValue
        } as T
    } catch (e: Exception) {
        defaultValue
    }
}

inline fun JSONArray.forEach(action: (JSONObject) -> Unit) {
    for (i in 0 until length()) {
        action(getJSONObject(i))
    }
}

inline fun JSONArray.forEachIndexed(action: (index: Int, jsonObject: JSONObject) -> Unit) {
    for (i in 0 until length()) {
        action(i, getJSONObject(i))
    }
}