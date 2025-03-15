package com.avelon.chatoyant.ui.mapbox

import android.location.Location
import android.location.LocationListener
import com.avelon.chatoyant.logging.DLog

class MapboxLocationListener: LocationListener {
    private val TAG = DLog.forTag(LocationListener::class.java)

    override fun onLocationChanged(location: Location) {
        DLog.d(TAG, "onLocationChanged(): ${location.latitude}:${location.longitude} ${location.bearing}")
       /*
       DLog.d(TAG, "onLocationChanged(): ${location.bearing}")
        DLog.d(TAG, "onLocationChanged(): ${location.time}")
        DLog.d(TAG, "onLocationChanged(): ${location.accuracy}")
        DLog.d(TAG, "onLocationChanged(): ${location.bearingAccuracyDegrees}")
        DLog.d(TAG, "onLocationChanged(): ${location.elapsedRealtimeAgeMillis}")
        DLog.d(TAG, "onLocationChanged(): ${location.elapsedRealtimeNanos}")
        DLog.d(TAG, "onLocationChanged(): ${location.elapsedRealtimeUncertaintyNanos}")
        DLog.d(TAG, "onLocationChanged(): ${location.latitude}")
        DLog.d(TAG, "onLocationChanged(): ${location.longitude}")
        DLog.d(TAG, "onLocationChanged(): ${location.speed}")
        DLog.d(TAG, "onLocationChanged(): ${location.speedAccuracyMetersPerSecond}")
        DLog.d(TAG, "onLocationChanged(): ${location.verticalAccuracyMeters}")
        */
    }
}