package com.avelon.chatoyant

import android.Manifest
import android.app.Service
import android.content.Intent
import android.location.LocationManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.avelon.chatoyant.crosscutting.DLog
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class MainService : Service() {
    companion object {
        private val TAG = DLog.forTag(MainService::class.java)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onCreate() {
        DLog.d(TAG, "onCreate()")
        super.onCreate()

        val writer = OutputStreamWriter(FileOutputStream("/data/local/tmp/nmea.log"))
        writer.write("--------")

        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.addNmeaListener({ s: String, l: Long ->
            DLog.i(TAG, "nmea s=$s, l=$l")
            writer.write(s)
        }, Handler())

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, {
            DLog.i(TAG, "location")
        }, Looper.getMainLooper())
    }

    override fun onBind(intent: Intent): IBinder {
        DLog.d(TAG, "onBind()")
        TODO("NOT IMPLEMENTED")
    }
}
