package com.want.network.config

import com.want.common.CommonUtil
import com.want.common.log.CLog
import com.want.network.util.JsonUtil
import okhttp3.*
import okio.Buffer
import okio.BufferedSource
import java.net.URLDecoder
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

class LogInterceptor(block: (LogInterceptor.() -> Unit)? = null) : Interceptor {
    private var logLevel: LogLevel = LogLevel.NONE//打印日期的标记
    private var colorLevel: ColorLevel = ColorLevel.DEBUG//默认是debug级别的logcat
    private var logTag = TAG//日志的Logcat的Tag

    init {
        block?.invoke(this)
    }

    /**
     * 设置LogLevel
     */
    fun logLevel(level: LogLevel): LogInterceptor {
        logLevel = level
        return this
    }

    /**
     * 设置colorLevel
     */
    fun colorLevel(level: ColorLevel): LogInterceptor {
        colorLevel = level
        return this
    }

    /**
     * 设置Log的Tag
     */
    fun logTag(tag: String): LogInterceptor {
        logTag = tag
        return this
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        //请求
        val request = chain.request()
        //响应
        return kotlin.runCatching { chain.proceed(request) }
            .onFailure {
                it.printStackTrace()
                logIt(
                    it.message.toString(),
                    ColorLevel.ERROR
                )
            }.onSuccess { response ->
                if (logLevel == LogLevel.NONE) {
                    return response
                }
                //记录请求日志
                logRequest(request, chain.connection())
                //记录响应日志
                logResponse(response)
            }.getOrThrow()
    }

    /**
     * 记录请求日志
     */
    private fun logRequest(request: Request, connection: Connection?) {
        val sb = StringBuilder()
        sb.appendln("\r\n")
        sb.appendln("->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->")
        when (logLevel) {
            LogLevel.NONE -> {
                /*do nothing*/
            }
            LogLevel.BASIC -> {
                logBasicReq(sb, request, connection)
            }
            LogLevel.HEADERS -> {
                logHeadersReq(sb, request, connection)
            }
            LogLevel.BODY -> {
                logBodyReq(sb, request, connection)
            }
        }
        sb.appendln("->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->")
        logIt(sb)
    }

    //region log  request

    private fun logBodyReq(
        sb: StringBuilder,
        request: Request,
        connection: Connection?
    ) {
        logHeadersReq(sb, request, connection)
        //读取出request Body的内容
        val req = request.newBuilder().build()
        val sink = Buffer()
        req.body?.writeTo(sink)
        sb.appendln("RequestBody: ${sink.readUtf8()}")
    }

    private fun logHeadersReq(
        sb: StringBuilder,
        request: Request,
        connection: Connection?
    ) {
        logBasicReq(sb, request, connection)
        val headersStr = request.headers.joinToString("") { header ->
            "请求 Header: {${header.first}=${header.second}}\n"
        }
        sb.appendln(headersStr)
    }

    private fun logBasicReq(
        sb: StringBuilder,
        request: Request,
        connection: Connection?
    ) {
        sb.appendln("请求 method: ${request.method} url: ${decodeUrlStr(request.url.toString())} tag: ${request.tag()} protocol: ${connection?.protocol() ?: Protocol.HTTP_1_1}")
    }

    //endregion

    /**
     * 记录响应日志
     * [response] 响应数据
     */
    private fun logResponse(response: Response) {
        val sb = StringBuffer()
        sb.appendln("\r\n")
        sb.appendln("<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<")
        when (logLevel) {
            LogLevel.NONE -> {
                /*do nothing*/
            }
            LogLevel.BASIC -> {
                logBasicRsp(sb, response)
            }
            LogLevel.HEADERS -> {
                logHeadersRsp(response, sb)
            }
            LogLevel.BODY -> {
                logHeadersRsp(response, sb)
                //body.string会抛IO异常
                kotlin.runCatching {
                    //peek类似于clone数据流，监视，窥探,不能直接用原来的body的string流数据作为日志，会消费掉io，所以这里是peek，监测
//                    val peekBody = response.peekBody(1024 * 1024)
//                    sb.appendln(UnicodeUtil.unicodeDecode(peekBody.string()))

                    val responseBody:ResponseBody? = response.body
                    val source: BufferedSource? = responseBody?.source()
                    source?.request(Long.MAX_VALUE)
                    val buffer:Buffer? = source?.buffer
                    val UTF8:Charset = Charset.forName("UTF-8")
                    val readString = buffer?.clone()?.readString(UTF8)
                    sb.appendln("响应 Body: "+ readString?.let { JsonUtil.formatJson(JsonUtil.decodeUnicode(it)) })
                }.getOrNull()
            }
        }
        sb.appendln("<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<-<<")
        splitLog(sb, ColorLevel.INFO)
    }


    //region log response

    private fun logHeadersRsp(response: Response, sb: StringBuffer) {
        logBasicRsp(sb, response)
        val headersStr = response.headers.joinToString(separator = "") { header ->
            "响应 Header: {${header.first}=${header.second}}\n"
        }
        sb.appendln(headersStr)
    }

    private fun logBasicRsp(sb: StringBuffer, response: Response) {
        sb.appendln("响应 protocol: ${response.protocol} code: ${response.code} message: ${response.message}")
            .appendln("响应 request Url: ${decodeUrlStr(response.request.url.toString())}")
            .appendln("响应 sentRequestTime: ${
                toDateTimeStr(
                    response.sentRequestAtMillis,
                    MILLIS_PATTERN
                )
            }")
            .appendln("响应 receivedResponseTime: ${
                toDateTimeStr(
                    response.receivedResponseAtMillis,
                    MILLIS_PATTERN
                )
            }")
    }

    //endregion

    /**
     * 对于url编码的string 解码
     */
    private fun decodeUrlStr(url: String): String? {
        return kotlin.runCatching {
            URLDecoder.decode(url, "utf-8")
        }.onFailure { it.printStackTrace() }.getOrNull()
    }

    /**
     * 打印日志
     * [any]需要打印的数据对象
     * [tempLevel],便于临时调整打印color等级
     */
    private fun logIt(any: Any, tempLevel: ColorLevel? = null) {
        when (tempLevel ?: colorLevel) {
            ColorLevel.VERBOSE -> CLog.v(logTag, any.toString())
            ColorLevel.DEBUG -> CLog.d(logTag, any.toString())
            ColorLevel.INFO -> CLog.i(logTag, any.toString())
            ColorLevel.WARN -> CLog.w(logTag, any.toString())
            ColorLevel.ERROR -> CLog.e(logTag, any.toString())
        }
    }

    /**
     * 截取Log输出
     */
    private fun splitLog(sb: StringBuffer?, tempLevel: ColorLevel? = null) {
        var msg:String? = sb.toString()
        if (msg == null || msg.isEmpty()) return
        val segmentSize = 3 * 1024
        val length = msg.length.toLong()
        if (length <= segmentSize) { // 长度小于等于限制直接打印
            logIt(msg, tempLevel)
        } else {
            while (msg!!.length > segmentSize) { // 循环分段打印日志
                val logContent = msg.substring(0, segmentSize)
                msg = msg.replace(logContent, "")
                logIt(logContent, tempLevel)
            }
            logIt(msg, tempLevel) // 打印剩余日志
        }
    }

    companion object {
        private const val TAG = CommonUtil.TAG+"<NetWork>"//默认的TAG

        //时间格式化
        const val MILLIS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSXXX"

        //转化为格式化的时间字符串
        fun toDateTimeStr(millis: Long, pattern: String): String {
            return SimpleDateFormat(pattern, Locale.getDefault()).format(millis)
        }
    }


    /**
     * 打印日志的范围
     */
    enum class LogLevel {
        NONE,//不打印
        BASIC,//只打印行首，请求/响应
        HEADERS,//打印请求和响应的 所有 header
        BODY,//打印所有
    }

    /**
     * Log颜色等级，应用于Android Logcat分为 v、d、i、w、e
     */
    enum class ColorLevel {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

}