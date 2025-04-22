package com.avelon.chatoyant.ui.projection

import android.app.Activity.RESULT_OK
import android.content.Context
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.databinding.FragmentProjectionBinding
import com.avelon.chatoyant.logging.DLog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class ProjectionFragment : Fragment() {
    companion object {
        private val TAG = DLog.forTag(ProjectionFragment::class.java)
    }

    private var _binding: FragmentProjectionBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProjectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // DAJO
        val mediaProjectionManager = context?.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        // context?.startForegroundService(Intent(context, ProjectionService::class.java))

        val startMediaProjection =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val mediaProjection = mediaProjectionManager.getMediaProjection(result.resultCode, result.data!!)
                }
                DLog.d(TAG, "onActivityForResult()")
            }
        // startMediaProjection.launch(mediaProjectionManager.createScreenCaptureIntent())

        return root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        DLog.d(TAG, "onRequestPermissionsResult(); $requestCode")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun cameraPreviewScreen(modifier: Modifier = Modifier) {
        val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
        if (cameraPermissionState.status.isGranted) {
            CameraPreviewContent(modifier)
        } else {
            Column(
                modifier = modifier.fillMaxSize().wrapContentSize().widthIn(max = 480.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val textToShow =
                    if (cameraPermissionState.status.shouldShowRationale) {
                        // If the user has denied the permission but the rationale can be shown,
                        // then gently explain why the app requires this permission
                        "Whoops! Looks like we need your camera to work our magic!" +
                            "Don't worry, we just wanna see your pretty face (and maybe some cats).  " +
                            "Grant us permission and let's get this party started!"
                    } else {
                        // If it's the first time the user lands on this feature, or the user
                        // doesn't want to be asked again for this permission, explain that the
                        // permission is required
                        "Hi there! We need your camera to work our magic! ✨\n" +
                            "Grant us permission and let's get this party started! \uD83C\uDF89"
                    }
                Text(textToShow, textAlign = TextAlign.Center)
                Spacer(Modifier.height(16.dp))
                Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                    Text("Unleash the Camera!")
                }
            }
        }
    }

    @Composable
    private fun cameraPreviewContent(modifier: Modifier = Modifier) {
        // TODO: Implement
    }
}
