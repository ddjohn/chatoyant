package com.avelon.chatoyant.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.app.NotificationCompat
import com.avelon.chatoyant.R
import com.avelon.chatoyant.logging.DLog

class Notif(
    val ctx: Context,
) {
    companion object {
        private val TAG = DLog.forTag(Notif::class.java)
        val channels =
            listOf(
                NotificationChannel("H", "High", NotificationManager.IMPORTANCE_HIGH),
                NotificationChannel("L", "Low", NotificationManager.IMPORTANCE_LOW),
                // NotificationChannel("Max", "Max", NotificationManager.IMPORTANCE_MAX),
                NotificationChannel("Min", "Min", NotificationManager.IMPORTANCE_MIN),
                NotificationChannel("N", "None", NotificationManager.IMPORTANCE_NONE),
                NotificationChannel("D", "Default", NotificationManager.IMPORTANCE_DEFAULT),
                // xNotificationChannel("U", "Unspecified", NotificationManager.IMPORTANCE_UNSPECIFIED),
            )
    }

    init {
        val notificationManager = ctx.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannels(channels)
    }

    fun default(
        id: Int,
        title: String,
        content: String,
    ) {
        val notificationManager = ctx.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        var builder =
            NotificationCompat
                .Builder(ctx, "D")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notificationManager.notify(id, builder.build())
    }
}
