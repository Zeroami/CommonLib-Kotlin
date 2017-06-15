package com.zeroami.commonlib.http

import java.io.IOException

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import okio.ForwardingSink
import okio.Okio
import okio.Sink

/**
 * 带进度信息的RequestBody
 *
 * @author Zeroami
 */
class LProgressRequestBody(
        private val requestBody: RequestBody,
        private val progressListener: (current:Long,total:Long) -> Unit) : RequestBody() {

    //包装完成的BufferedSink
    private var bufferedSink: BufferedSink? = null

    /**
     * 重写调用实际的响应体的contentType
     * @return MediaType
     */
    override fun contentType(): MediaType {
        return requestBody.contentType()
    }

    /**
     * 重写调用实际的响应体的contentLength
     * @return contentLength
     * *
     * @throws IOException 异常
     */
    @Throws(IOException::class)
    override fun contentLength(): Long {
        return requestBody.contentLength()
    }

    /**
     * 重写进行写入
     * @param sink BufferedSink
     * *
     * @throws IOException 异常
     */
    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(sink(sink))
        }
        requestBody.writeTo(bufferedSink)
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink?.flush()
    }

    /**
     * 写入，回调进度接口
     * @param sink Sink
     * *
     * @return Sink
     */
    private fun sink(sink: Sink): Sink {
        return object : ForwardingSink(sink) {
            //当前写入字节数
            internal var writtenBytesCount = 0L
            //总字节长度，避免多次调用contentLength()方法
            internal var totalBytesCount = 0L

            @Throws(IOException::class)
            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)
                //增加当前写入的字节数
                writtenBytesCount += byteCount
                //获得contentLength的值，后续不再调用
                if (totalBytesCount.toInt() == 0) {
                    totalBytesCount = contentLength()
                }
                progressListener(writtenBytesCount, totalBytesCount)
            }
        }
    }
}