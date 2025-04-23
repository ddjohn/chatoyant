package com.avelon.chatoyant

import android.content.Context
import android.os.Bundle
import android.os.DropBoxManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.avelon.chatoyant.databinding.ActivityMainBinding
import com.avelon.chatoyant.logging.DLog
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.navigation_home,
                    R.id.navigation_mapbox,
                    R.id.navigation_notifications,
                ),
            )
        // setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            DLog.d(TAG, "onDestinationChanged(): ${destination.label}")
            val dropboxManager = getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager
            dropboxManager.addText("fragment", "${destination.label}")
        }

        // DAJO
        // supportActionBar?.hide()

        requestPermissions(REQUEST_PERMISSIONS, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        DLog.d(TAG, "onRequestPermissionsResult(): $requestCode")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
