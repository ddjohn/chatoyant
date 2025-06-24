package com.avelon.chatoyant

import android.os.Bundle
import android.os.DropBoxManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = DLog.forTag(MainActivity::class.java)

        private val REQUEST_PERMISSIONS =
            arrayOf(
                "android.car.permission.READ_CAR_OCCUPANT_AWARENESS_STATE",
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.CAMERA",
                "android.permission.POST_NOTIFICATIONS",
            )
        private const val REQUEST_CODE = 666
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        DLog.d(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        supportActionBar?.hide()

        setupNightMode()
        setupNavigationBar()

        requestPermissions(REQUEST_PERMISSIONS, REQUEST_CODE)
    }

    private fun setupNavigationBar() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            DLog.d(TAG, "onDestinationChanged(): ${destination.label}")
            val dropboxManager = getSystemService(DROPBOX_SERVICE) as DropBoxManager
            dropboxManager.addText("fragment", "${destination.label}")
        }
    }

    private fun setupNightMode() {
        if (Calendar.getInstance().get(Calendar.MINUTE) % 2 == 0) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        DLog.d(TAG, "onRequestPermissionsResult(): $requestCode")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (i in 0..permissions.size - 1) {
            DLog.i(TAG, "permission=${permissions.get(i)}, grant=${grantResults.get(i)}")
        }
    }
}
