package com.avelon.chatoyant.ui.displays

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder

class CanvasDisplay(
    val context: Context?,
) : SurfaceHolder.Callback {
    companion object {
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        surfaceHolder.setFormat(-2) // TRANSPARENT

        val canvas = surfaceHolder.lockCanvas()

        val paint = Paint()
        paint.isAntiAlias = true
        paint.isDither = true
        paint.setColor(Color.YELLOW)
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 50f

        canvas.drawCircle(100f, 100f, 50f, paint)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    override fun surfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int,
    ) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}
}
