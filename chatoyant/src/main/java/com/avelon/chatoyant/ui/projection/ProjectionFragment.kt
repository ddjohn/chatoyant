package com.avelon.chatoyant.ui.projection

import android.app.Activity.RESULT_OK
import android.content.Context
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.databinding.FragmentProjectionBinding
import com.avelon.chatoyant.logging.DLog

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
