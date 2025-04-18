package com.avelon.chatoyant

import android.content.Context
import android.os.Bundle
import android.os.DropBoxManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.avelon.chatoyant.databinding.ActivityMainBinding
import com.avelon.chatoyant.logging.DLog
import com.avelon.chatoyant.notifications.Notif
import com.avelon.chatoyant.permissions.Permissions
import com.google.android.material.bottomnavigation.BottomNavigationView
import dalvik.system.DexClassLoader

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = DLog.forTag(MainActivity::class.java)
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
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                DLog.d(TAG, "onDestinationChanged(): ${destination.label}")
                val dropboxManager = getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager
                dropboxManager.addText("fragment", "${destination.label}")
            },
        )

        // DAJO
        supportActionBar?.hide()

        val cl = Thread.currentThread().getContextClassLoader() as ClassLoader
        DLog.e(TAG, "class=$cl")
        val s = DexClassLoader.getSystemClassLoader() as ClassLoader
        DLog.e(TAG, "class=$s")

        for (resource in s.getResources("")) {
            DLog.e(TAG, "resource=$resource")
        }

        val notif = Notif(this)
        notif.default(667, "title2", "content2")

        // val field = s.javaClass.getDeclaredField("classes") as Field
        // field.isAccessible = true

        // val classes =  field.get(classLoader)
        // DLog.e(TAG, "classess=${classes}")

        val permissions = Permissions(this)
        permissions.requestPermissions()
        permissions.checkSelfPermssions()
    }
}
