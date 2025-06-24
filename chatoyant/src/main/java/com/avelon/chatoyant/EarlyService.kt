package com.avelon.chatoyant

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.avelon.chatoyant.crosscutting.DLog

class EarlyService : Service() {
    companion object {
        val TAG = DLog.forTag(EarlyService::class.java)
    }

    override fun onCreate() {
        DLog.d(TAG, "onCreate()")
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        DLog.d(TAG, "onBind(): $intent")
        TODO("Return the communication channel to the service.")
    }
}
