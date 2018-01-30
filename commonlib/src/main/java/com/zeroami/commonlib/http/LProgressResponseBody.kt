package com.zeroami.commonlib.http

import java.io.IOException

import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Okio

/**
 * 带进度信息的ResponseBody
 *
 * @author Zeroami
 */
class LProgressResponseBody(private val originalResponse: Response,
                            private val progressListener: (current: Long, total: Long) -> Unit) : ResponseBody() {
    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType {
        return originalResponse.body().contentType()
    }

    override fun contentLength(): Long {
        return originalResponse.body().contentLength()
    }

    override fun source(): BufferedSource? {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(object : ForwardingSource(originalResponse.body().source()) {
                internal var bytesReadedCount = 0L
                //总字节长度，避免多次调用contentLength()方法
                internal var totalBytesCount = 0L

                @Throws(IOException::class)
                override fun read(sink: Buffer, byteCount: Long): Long {
                    val bytesRead = super.read(sink, byteCount)
                    bytesReadedCount += if (bytesRead.toInt() == -1) 0 else bytesRead
                    //获得contentLength的值，后续不再调用
                    if (totalBytesCount.toInt() == 0) {
                        totalBytesCount = contentLength()
                    }
                    progressListener(bytesReadedCount, totalBytesCount)
                    return bytesRead
                }
            })
        }
        return bufferedSource
    }
}