package com.avelon.chatoyant

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import androidx.media.MediaBrowserServiceCompat
import com.avelon.chatoyant.crosscutting.DLog

class MainMediaBrowserService : MediaBrowserServiceCompat() {
    companion object {
        val TAG = DLog.forTag(MainMediaBrowserService::class.java)
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?,
    ): BrowserRoot? {
        DLog.d(TAG, "onGetRoot()")
        TODO("Not yet implemented")
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<List<MediaBrowserCompat.MediaItem?>?>,
    ) {
        DLog.d(TAG, "onLoadChildren()")
        TODO("Not yet implemented")
    }
}
