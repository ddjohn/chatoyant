package com.avelon.chatoyant.ui.camera

import android.content.ContentValues
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.databinding.FragmentCameraBinding
import com.avelon.chatoyant.logging.DLog
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {
    companion object {
        private val TAG = DLog.forTag(CameraFragment::class.java)
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    private var _binding: FragmentCameraBinding? = null
    val binding get() = _binding!!

    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        DLog.d(TAG, "onCreateView()")

        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.imageCaptureButton.setOnClickListener { takePhoto() }
        binding.videoCaptureButton.setOnClickListener { captureVideo() }

        cameraExecutor = Executors.newSingleThreadExecutor()

        startCamera()

        return root
    }

    override fun onDestroyView() {
        DLog.d(TAG, "onDestroyView()")
        super.onDestroyView()
        _binding = null
    }

    private fun startCamera() {
        DLog.d(TAG, "startCamera()")

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            DLog.d(TAG, "listener()")

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview =
                Preview
                    .Builder()
                    .build()
                    .also { it.setSurfaceProvider(binding.viewFinder.surfaceProvider) }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch (e: Exception) {
                DLog.e(TAG, "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        DLog.d(TAG, "takePhoto()")

        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
        val contentValues =
            ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }

        val outputOptions =
            ImageCapture.OutputFileOptions
                .Builder(requireContext().contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    DLog.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    DLog.d(TAG, msg)
                }
            },
        )
    }

    private fun captureVideo() {
        DLog.d(TAG, "captureVideo()")
    }
}
