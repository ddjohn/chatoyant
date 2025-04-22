package com.avelon.chatoyant.ui.projection

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.IBinder
import android.os.Parcelable
import com.avelon.chatoyant.R
import com.avelon.chatoyant.logging.DLog

class ProjectionService : Service() {
    companion object {
        private val TAG = DLog.forTag(ProjectionService::class.java)
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        DLog.d(TAG, "onStartCommand()")

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel("id", "Name", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)

        val notificationBuilder = Notification.Builder(this, "id") as Notification.Builder
        notificationBuilder.setSmallIcon(R.drawable.ic_notifications_black_24dp)
        notificationBuilder.setContentText("Projection")
        notificationBuilder.setContentText("Projection")
        notificationBuilder.setPriority(Notification.PRIORITY_DEFAULT)

        startForeground(888, notificationBuilder.build())

        val bundle = intent?.extras
        val resultCode = bundle?.getInt("EXTRA_CODE")
        val data = bundle?.getParcelable<Parcelable>("EXTRA_DATA") as Intent?
        DLog.i(TAG, "result=$resultCode")
        DLog.i(TAG, "data=$data")

        DLog.i(TAG, "Launching projection...")
        val mediaManager = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val projection = mediaManager.getMediaProjection(resultCode!!, data!!)
        DLog.i(TAG, "Projection: $projection")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        DLog.d(TAG, "onCreate()")
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
