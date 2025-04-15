package com.avelon.chatoyant.permissions

import android.app.Activity
import com.avelon.chatoyant.logging.DLog

class Permissions(
    val ctx: Activity,
) {
    companion object {
        private val TAG = DLog.forTag(Permissions::class.java)
        private val REQUEST_CODE = 666
        private val REQUEST_PERMISSIONS =
            arrayOf(
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.CAMERA",
                "android.permission.RECORD_AUDIO",
                "android.permission.POST_NOTIFICATIONS",
            )
    }

    public fun requestPermissions() {
        ctx.requestPermissions(REQUEST_PERMISSIONS, REQUEST_CODE)
    }

    public fun checkSelfPermssions() {
        for (permission in REQUEST_PERMISSIONS) {
            ctx.checkSelfPermission(permission)
        }
    }
}
