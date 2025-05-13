package com.avelon.chatoyant.ui.displays

import android.car.Car
import android.car.CarOccupantZoneManager
import android.car.CarOccupantZoneManager.DISPLAY_TYPE_INSTRUMENT_CLUSTER
import android.car.CarOccupantZoneManager.OCCUPANT_TYPE_DRIVER
import android.car.VehicleAreaSeat.SEAT_ROW_1_LEFT
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.databinding.FragmentDisplaysBinding
import com.avelon.chatoyant.logging.DLog

class DisplaysFragment : Fragment() {
    companion object {
        private val TAG = DLog.forTag(DisplaysFragment::class.java)
    }

    private var _binding: FragmentDisplaysBinding? = null
    val binding get() = _binding!!

    @RequiresPermission("android.car.permission.READ_CAR_OCCUPANT_AWARENESS_STATE")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDisplaysBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val surfaceView = binding.surfaceView
        surfaceView.setZOrderOnTop(true)
        // surfaceView.holder.setFormat(PixelFormat.TRANSPARENT);
        surfaceView.holder.addCallback(ActivityDisplay(context))

        val surfaceView2 = binding.surfaceView2
        surfaceView2.setZOrderOnTop(true)
        // surfaceView.holder.setFormat(PixelFormat.TRANSPARENT);
        surfaceView2.holder.addCallback(CanvasDisplay(context))

        val packageManager = context?.packageManager as PackageManager
        for (pack in packageManager.getInstalledPackages(PackageManager.MATCH_ALL)) {
            DLog.e(TAG, "package=$pack")
            val intent = packageManager.getLaunchIntentForPackage(pack.packageName)
            DLog.e(TAG, "intent=$intent")
        }

        if (context?.packageManager?.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE) == false) {
            DLog.w(TAG, "No autmotive feature")
            return root
        }

        val car = Car.createCar(context)
        val occupantZoneManager = car?.getCarManager(Car.CAR_OCCUPANT_ZONE_SERVICE) as CarOccupantZoneManager
        val zoneInfo = CarOccupantZoneManager.OccupantZoneInfo(1, OCCUPANT_TYPE_DRIVER, SEAT_ROW_1_LEFT)

        for (zone in occupantZoneManager.allOccupantZones) {
            if (zone.occupantType == DISPLAY_TYPE_INSTRUMENT_CLUSTER) {
                DLog.e(TAG, "cluster zone=$zone")
            } else {
                DLog.e(TAG, "driver zone=$zone")
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
