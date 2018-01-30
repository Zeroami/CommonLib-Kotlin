package com.zeroami.commonlib.utils

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import com.zeroami.commonlib.CommonLib
import okhttp3.ResponseBody
import java.io.*


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
     */
    fun checkSDCardAvailable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * 删除文件或递归删除文件夹
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
     */
    fun getFileSize(file: File): Long {
        return if (file.exists() && file.isFile) file.length() else -1
    }

    /**
     * 获取文件名
     */
    fun getFileName(filePath: String): String {
        if (TextUtils.isEmpty(filePath)) {
            return ""
        }

        val pos = filePath.lastIndexOf(File.separator)
        return if (pos == -1) filePath else filePath.substring(pos + 1)
    }

    /**
     * 获取文件夹名称
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
     * 获取不重复文件名
     */
    fun getNoRepeatFileName(dirPath: String, fileName: String): String {
        val dir = File(dirPath)
        var newFileName = fileName
        val fileNameWithoutExt = getFileNameWithoutExt(fileName)
        val ext = if (getFileExtension(fileName).isNotEmpty()) "." + getFileExtension(fileName) else ""
        val fileNames = dir.list() ?: return fileName
        var i = 1
        while (fileNames.contains(newFileName)) {
            newFileName = "$fileNameWithoutExt($i)$ext"
            i++
        }
        return newFileName
    }

    /**
     * 获取文件名（不带后缀）
     */
    fun getFileNameWithoutExt(filePath: String): String {
        val fileName = getFileName(filePath)
        val extPos = fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR)
        if (extPos == -1) {
            return fileName
        }
        return fileName.substring(0, extPos)
    }

    /**
     * 创建目录
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
     */
    fun isFolderExist(directoryPath: String): Boolean {
        if (TextUtils.isEmpty(directoryPath)) {
            return false
        }

        val dire = File(directoryPath)
        return dire.exists() && dire.isDirectory
    }

    /**
     * 写入网络响应结果到本地
     */
    fun writeResponseBodyToDisk(body: ResponseBody, path: String): Boolean {
        makeDirs(path)
        val file = File(path)
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            try {
                val fileReader = ByteArray(1024 * 1024)
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(file)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                }
                outputStream.flush()
                return true
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            } finally {
                if (inputStream != null) {
                    inputStream.close()
                }
                if (outputStream != null) {
                    outputStream.close()
                }
            }
        } catch (e: IOException) {
            return false
        }
    }

    /**
     * 扫描文件到媒体库
     */
    fun scanFile(filePath: String) {
        CommonLib.ctx.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)))
    }

    /**
     * 写入对象
     */
    fun writeObjectToFile(obj: Serializable, path: String) {
        makeDirs(path)
        val file = File(path)
        val out: FileOutputStream
        try {
            out = FileOutputStream(file)
            val objOut = ObjectOutputStream(out)
            objOut.writeObject(obj)
            objOut.flush()
            objOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 读取对象
     */
    fun readObjectFromFile(path: String): Any? {
        var temp: Any? = null
        val file = File(path)
        val input: FileInputStream
        try {
            input = FileInputStream(file)
            val objIn = ObjectInputStream(input)
            temp = objIn.readObject()
            objIn.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return temp
    }
}