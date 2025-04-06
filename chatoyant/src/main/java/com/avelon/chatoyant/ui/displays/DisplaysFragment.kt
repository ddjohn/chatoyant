package com.avelon.chatoyant.ui.displays

import android.content.Context
import android.graphics.Color
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Surface
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.databinding.FragmentHomeBinding
import com.avelon.chatoyant.logging.DLog
import com.google.zxing.BarcodeFormat
import java.io.BufferedWriter
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import android.graphics.Paint
import android.graphics.PixelFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlin.concurrent.thread

class DisplaysFragment : Fragment(), SurfaceHolder.Callback {
    private val TAG = DLog.forTag(DisplaysFragment::class.java)

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val surfaceView = binding.surfaceView
        val surfaceHolder = surfaceView.holder
        val surface = surfaceHolder.surface

        surfaceView.setZOrderOnTop(true)
        //surfaceView.holder.setFormat(PixelFormat.TRANSPARENT);

        surfaceView.holder.addCallback(this)

        val displayManager = context?.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager

        val vDisplay = displayManager.createVirtualDisplay("test", 600, 400, 121, surface,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC or
            DisplayManager.VIRTUAL_DISPLAY_FLAG_SECURE or
            DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or
            DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION
            //DisplayManager.VIRTUAL_DISPLAY_FLAG_ROTATES_WITH_CONTENT or
            //DisplayManager.VIRTUAL_DISPLAY_FLAG_TRUSTED or
            //DisplayManager.VIRTUAL_DISPLAY_FLAG_SUPPORTS_TOUCH
            /*DisplayManager. VIRTUAL_DISPLAY_FLAG_OWN_FOCUS 1 shl 14*/
        )

        for(display in displayManager.displays) {
            DLog.i(TAG, "display=${display}")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        DLog.d(TAG, "surfaceCreated()")
        val canvas = holder.lockCanvas()

        val paint = Paint()
        paint.isAntiAlias = true
        paint.isDither = true
        paint.setColor(Color.YELLOW)
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 50f

        canvas.drawCircle(100f, 100f, 50f, paint)
        holder.unlockCanvasAndPost(canvas)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}
}