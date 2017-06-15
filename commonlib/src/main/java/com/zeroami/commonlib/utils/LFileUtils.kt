package com.zeroami.commonlib.utils

import android.os.Environment
import android.text.TextUtils

import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileFilter
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.util.ArrayList
import java.util.HashMap

/**
 * 文件工具类
 *
 * @author Zeroami
 */
object LFileUtils {

    private val FILENAME_REGIX = "^[^\\/?\"*:<>\\]{1,255}$"

    val FILE_EXTENSION_SEPARATOR = "."


    /**
     * 是否存在SDCard
     * @return
     */
    fun checkSDCardAvailable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * 删除文件或递归删除文件夹
     * @param path
     * @return
     */
    fun deleteFile(path: String): Boolean {
        if (TextUtils.isEmpty(path)) {
            return false
        }
        val file = File(path)
        return deleteFile(file)
    }

    fun deleteFile(file: File): Boolean {
        if (!file.exists()) {
            return false
        }
        if (file.isFile) {
            return file.delete()
        }
        if (file.isDirectory) {
            for (f in file.listFiles()) {
                if (f.isFile) {
                    f.delete()
                } else if (f.isDirectory) {
                    deleteFile(f)
                }
            }
        }
        return file.delete()
    }


    /**
     * 重命名文件和文件夹
     * @param file
     * @param newFileName
     * @return
     */
    fun renameFile(file: File, newFileName: String): Boolean {
        if (newFileName.matches(FILENAME_REGIX.toRegex())) {
            var newFile: File? = null
            if (file.isDirectory) {
                newFile = File(file.parentFile, newFileName)
            } else {
                val temp = newFileName + file.name.substring(file.name.lastIndexOf('.'))
                newFile = File(file.parentFile, temp)
            }
            if (file.renameTo(newFile)) {
                return true
            }
        }
        return false
    }

    /**
     * 获取文件大小，不存在返回-1
     * @param path
     * @return
     */
    fun getFileSize(path: String): Long {
        if (TextUtils.isEmpty(path)) {
            return -1
        }

        val file = File(path)
        return getFileSize(file)
    }


    /**
     * 获取文件大小，不存在返回-1
     * @param file
     * @return
     */
    fun getFileSize(file: File): Long {
        return if (file.exists() && file.isFile) file.length() else -1
    }

    /**
     * 获取文件名
     * @param filePath
     * @return
     */
    fun getFileName(filePath: String): String {
        if (TextUtils.isEmpty(filePath)) {
            return ""
        }

        val pos = filePath.lastIndexOf(File.separator)
        return if (pos == -1) "" else filePath.substring(pos + 1)
    }

    /**
     * 获取文件夹名称
     * @param filePath
     * @return
     */
    fun getFolderName(filePath: String): String {
        if (TextUtils.isEmpty(filePath)) {
            return ""
        }

        val pos = filePath.lastIndexOf(File.separator)
        return if (pos == -1) "" else filePath.substring(0, pos)
    }

    /**
     * 获取扩展名
     * @param filePath
     * @return
     */
    fun getFileExtension(filePath: String): String {
        if (TextUtils.isEmpty(filePath)) {
            return filePath
        }

        val extPos = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR)
        val filePos = filePath.lastIndexOf(File.separator)
        if (extPos == -1) {
            return ""
        }
        return if (filePos >= extPos) "" else filePath.substring(extPos + 1)
    }

    /**
     * @param filePath
     * @return
     */
    fun makeDirs(filePath: String): Boolean {
        val folderName = getFolderName(filePath)
        if (TextUtils.isEmpty(folderName)) {
            return false
        }

        val folder = File(folderName)
        return folder.exists() && folder.isDirectory || folder
                .mkdirs()
    }


    /**
     * 判断文件是否存在
     * @param filePath
     * @return
     */
    fun isFileExist(filePath: String): Boolean {
        if (TextUtils.isEmpty(filePath)) {
            return false
        }

        val file = File(filePath)
        return file.exists() && file.isFile
    }

    /**
     * 判断文件夹是否存在
     * @param directoryPath
     * @return
     */
    fun isFolderExist(directoryPath: String): Boolean {
        if (TextUtils.isEmpty(directoryPath)) {
            return false
        }

        val dire = File(directoryPath)
        return dire.exists() && dire.isDirectory
    }
}