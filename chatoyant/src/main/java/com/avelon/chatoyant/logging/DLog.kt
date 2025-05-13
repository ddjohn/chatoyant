package com.avelon.chatoyant.logging

import android.util.Log

class DLog {
    companion object {
        fun forTag(clazz: Class<*>): String = "@CHAT." + clazz.simpleName

        fun e(
            tag: String,
            message: String,
        ) {
            Log.e(tag, message)
        }

        fun i(
            tag: String,
            message: String,
        ) {
            Log.i(tag, message)
        }

        fun d(
            tag: String,
            message: String,
        ) {
            Log.d(tag, message)
        }

        fun e(
            tag: String,
            message: String,
            e: Exception,
        ) {
            Log.e(tag, message, e)
        }

        fun w(
            tag: String,
            message: String,
        ) {
            Log.w(tag, message)
        }
    }
}
