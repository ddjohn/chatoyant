package com.avelon.chatoyant.ui.home

import com.avelon.chatoyant.crosscutting.DLog
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class WebClient(
    host: String,
    port: Int,
) : Socket(host, port) {
    companion object {
        private val TAG = DLog.forTag(WebClient::class.java)
    }

    fun run() {
        DLog.d(TAG, "run()")

        val reader = BufferedReader(InputStreamReader(inputStream))
        val writer = BufferedWriter(OutputStreamWriter(outputStream))

        writer.write("GET / HTTP/1.0\r\n")
        writer.write("\r\n")

        val message = reader.readLine()

        DLog.i(TAG, "received: $message")

        reader.close()
        writer.close()
        close()
    }
}
