package com.avelon.chatoyant.logging

import android.util.Log

class DLog {
    companion object {
        fun forTag(clazz: Class<*>): String {
            return "@CHAT." + clazz.simpleName;
        }

        fun e(tag: String, message: String) {
            Log.e(tag, message)
        }

        fun i(tag: String, message: String) {
            Log.e(tag, message)
        }

        fun d(tag: String, message: String) {
            Log.e(tag, message)
        }
    }
}
