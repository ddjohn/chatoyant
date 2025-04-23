package com.avelon.chatoyant.ui.displays

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.util.Log
import android.view.SurfaceHolder
import com.avelon.chatoyant.logging.DLog

class ActivityDisplay(
    val context: Context?,
) : SurfaceHolder.Callback {
    companion object {
        private val TAG = DLog.forTag(ActivityDisplay::class.java)
        private const val VIRTUAL_DISPLAY_FLAG_SUPPORTS_TOUCH = 1 shl 6
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        val displayManager = context?.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager

        val vDisplay = createAptivDisplay("activity", surfaceHolder) as VirtualDisplay

        for (display in displayManager.displays) {
            DLog.i(TAG, "display=$display")
        }

        DLog.i(TAG, "vDisplay=$vDisplay")
        DLog.i(TAG, "display=${vDisplay.display}")
        DLog.i(TAG, "surface=${vDisplay.surface}")

        startActivity(vDisplay, "com.google.android.car.kitchensink")
    }

    override fun surfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int,
    ) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    private fun createAptivDisplay(
        name: String,
        surfaceHolder: SurfaceHolder,
    ): VirtualDisplay {
        val displayManager = context?.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager

        val canvas = surfaceHolder.lockCanvas()
        val width = canvas.width
        val height = canvas.height
        val dpi = 121 // canvas.density
        surfaceHolder.unlockCanvasAndPost(canvas)

        DLog.i(TAG, "size=${width}x$height:$dpi")
        val virtualDisplay =
            displayManager.createVirtualDisplay(
                name,
                width,
                height,
                dpi,
                surfaceHolder.surface,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC or
                    VIRTUAL_DISPLAY_FLAG_SUPPORTS_TOUCH,
            )

        val d = displayManager.getDisplay(22)
        Log.e(TAG, "display22=$d")

        val d2 = displayManager.getDisplays("activity")
        Log.e(TAG, "display22=$d2")

        /*
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC
                  //  DisplayManager.VIRTUAL_DISPLAY_FLAG_SECURE or
                  //  DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or
                  //  DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION
            //DisplayManager.VIRTUAL_DISPLAY_FLAG_ROTATES_WITH_CONTENT or
            //DisplayManager.VIRTUAL_DISPLAY_FLAG_TRUSTED or
            //DisplayManager.VIRTUAL_DISPLAY_FLAG_SUPPORTS_TOUCH
            /*DisplayManager. VIRTUAL_DISPLAY_FLAG_OWN_FOCUS 1 shl 14*/
         */

        return virtualDisplay
    }

    private fun startActivity(
        vDisplay: VirtualDisplay,
        packageName: String,
    ) {
        val packageManager = context?.packageManager

        val intent: Intent? = packageManager?.getLaunchIntentForPackage("com.google.android.car.kitchensink")
        DLog.i(TAG, "intent=$intent")

        val displayId: Int = vDisplay.display.getDisplayId()
        DLog.i(TAG, "displayId=$displayId")

        val options = ActivityOptions.makeBasic().setLaunchDisplayId(displayId)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK)

        context?.startActivity(intent, options.toBundle())
    }
}
