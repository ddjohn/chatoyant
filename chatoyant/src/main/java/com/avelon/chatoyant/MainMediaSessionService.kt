package com.avelon.chatoyant

import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.avelon.chatoyant.crosscutting.DLog

class MainMediaSessionService : MediaSessionService() {
    companion object {
        val TAG = DLog.forTag(MainMediaSessionService::class.java)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        DLog.d(TAG, "onGetSession(): $controllerInfo")
        TODO()
    }
}
