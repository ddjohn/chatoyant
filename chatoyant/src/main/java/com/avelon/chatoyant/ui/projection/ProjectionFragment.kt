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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
