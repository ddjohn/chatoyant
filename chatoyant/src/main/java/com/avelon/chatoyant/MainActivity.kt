package com.avelon.chatoyant

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.os.DropBoxManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.avelon.chatoyant.logging.DLog
import com.avelon.chatoyant.databinding.ActivityMainBinding
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

        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
/*
        activityManager.addApplicationStartInfoCompletionListener(mainExecutor, {
            DLog.e(TAG, "it=$it")
        })

        thread {
            while (true) {
                Thread.sleep(5000)
                DLog.e(TAG, "----------------------------")
                DLog.e(TAG, "apptasks:")
                for (task in activityManager.appTasks) {
                    DLog.e(TAG, "apptask=${task.taskInfo}")
                }

                DLog.e(TAG, "processes:")
                for (process in activityManager.runningAppProcesses) {
                    DLog.e(TAG, "process=${process.processName}")
                }

                DLog.e(TAG, "tasks2:")
                for (task in activityManager.getRecentTasks(5, RECENT_WITH_EXCLUDED)) {
                    DLog.e(TAG, "tasks2=$task")
                }

                val view = window.decorView.rootView
                view.isDrawingCacheEnabled = true
                val bitmap = Bitmap.createBitmap(view.getDrawingCache())
                view.isDrawingCacheEnabled = false

                DLog.e(TAG, "width=${bitmap.width}x${bitmap.height}")

                val metrics = DisplayMetrics()
                getWindowManager().getDefaultDisplay().getMetrics(metrics)
                DLog.e(TAG, "metrics=$metrics")

                val md = MessageDigest.getInstance("MD5")
                val buffer = ByteArrayOutputStream()

                for (x in 0..bitmap.width - 1) {
                    for (y in 0..bitmap.height - 1) {
                        buffer.write(bitmap[x, y])
                    }
                }
                md.update(buffer.toByteArray())
                val hash = md.digest()
                val hash2 = BigInteger(1, hash).toString(16)
                DLog.e(TAG, "hash=$hash2")
            }
        }

        runOnUiThread(
            Runnable {
                Thread.sleep(10000)
                val alertDialog = AlertDialog.Builder(this@MainActivity).create()
                alertDialog.setTitle("Alert")
                alertDialog.setMessage("Alert message to be shown")
            /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });*/
                alertDialog.show()
            },
        )
*/
        requestPermissions(REQUEST_PERMISSIONS, REQUEST_CODE)
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
