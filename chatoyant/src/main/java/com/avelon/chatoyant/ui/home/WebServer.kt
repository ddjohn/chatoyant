package com.avelon.chatoyant.ui.home

import com.avelon.chatoyant.crosscutting.DLog
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import kotlin.concurrent.thread

class WebServer(
    val port: Int = 8443,
) : ServerSocket(port) {
    companion object {
        private val TAG = DLog.forTag(WebServer::class.java)
    }

    fun run() {
        DLog.d(TAG, "run()")

        thread {
            while (true) {
                DLog.i(TAG, "Listening on $port")
                val socket = accept()
                val reader = BufferedReader(InputStreamReader(socket.inputStream))
                val writer = BufferedWriter(OutputStreamWriter(socket.outputStream))

                do {
                    val message = reader.readLine()
                    DLog.i(TAG, "in: $message")
                } while (message != null && !message.isEmpty())

                writer.write("HTTP/1.0 200 OK\r\n")
                writer.write("Content-Type: text/html\r\n")
                writer.write("\r\n")
                writer.write("<p>I am  server ${socket.port}</p>\r\n")

                // reader.close()
                // writer.close()
                // socket.close()
            }
        }
    }
}
