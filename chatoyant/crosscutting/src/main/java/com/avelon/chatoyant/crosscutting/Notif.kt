package com.avelon.chatoyant.crosscutting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

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
        val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannels(channels)
    }

    fun default(
        id: Int,
        title: String,
        content: String,
    ) {
        val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var builder =
            NotificationCompat
                .Builder(ctx, "D")
                .setSmallIcon(com.google.android.material.R.drawable.abc_ic_star_black_16dp)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notificationManager.notify(id, builder.build())
    }
}
