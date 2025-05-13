package com.avelon.chatoyant.ui.properties

import android.car.Car
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.databinding.FragmentPropertiesBinding

class PropertiesFragment :
    Fragment(),
    CarPropertyManager.CarPropertyEventCallback {
    companion object {
        private val TAG = DLog.forTag(PropertiesFragment::class.java)
    }

    private var _binding: FragmentPropertiesBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        DLog.d(TAG, "onCreateView()")

        _binding = FragmentPropertiesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (context?.packageManager?.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE) == false) {
            DLog.w(TAG, "No automotive feature")
            return root
        }

        val car = Car.createCar(context)
        val propertyManager = car?.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
        // propertyManager.subscribePropertyEvents(VehiclePropertyIds.PERF_VEHICLE_SPEED, CarPropertyManager.SENSOR_RATE_ONCHANGE, this)

        return root
    }

    override fun onDestroyView() {
        DLog.d(TAG, "onDestroyView()")
        super.onDestroyView()
        _binding = null
    }

    override fun onChangeEvent(p0: CarPropertyValue<*>?) {
        DLog.d(TAG, "onChangeEvent=$p0")
    }

    override fun onErrorEvent(
        p0: Int,
        p1: Int,
    ) {
        DLog.d(TAG, "onErrorEvent=$p0, $p1")
    }
}
