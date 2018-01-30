package com.zeroami.commonlib.utils

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * 加解密工具类

 * @author Zeroami
 */
object LCipherUtils {

    /**
     * MD5加密
     */
    fun md5(string: String): String {
        try {
            val hash = MessageDigest.getInstance("MD5").digest(
                    string.toByteArray(charset("UTF-8")))
            val hex = StringBuilder(hash.size * 2)
            for (b in hash) {
                if (b.toInt() and 0xFF < 0x10)
                    hex.append("0")
                hex.append(Integer.toHexString(b.toInt() and 0xFF))
            }
            return hex.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return ""
    }
}
