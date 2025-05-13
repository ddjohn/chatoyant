package com.avelon.chatoyant.ui.dropbox

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Bundle
import android.os.DropBoxManager
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.databinding.FragmentDropboxBinding
import com.avelon.chatoyant.logging.DLog
import kotlin.concurrent.thread

class DropboxFragment : Fragment() {
    companion object {
        private val TAG = DLog.forTag(DropboxFragment::class.java)
    }

    private var _binding: FragmentDropboxBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        DLog.d(TAG, "onCreateView()")

        _binding = FragmentDropboxBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val log = _binding?.log

        // log?.setScroller(Scroller(context));
        // log?.setMaxLines(1);
        log?.setVerticalScrollBarEnabled(true)
        log?.setMovementMethod(ScrollingMovementMethod())

        if (context?.checkSelfPermission(android.Manifest.permission.READ_DROPBOX_DATA) == PERMISSION_DENIED) {
            DLog.w(TAG, "Not all needed permissions are approved")
            return root
        }

        // Thread(Runnable() {  ->
        thread {
            var timeMillis = 0L
            val dropboxManager =
                context?.getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager

            while (true) {
                DLog.i(TAG, "runnable")
                Thread.sleep(1000)

                do {
                    DLog.i(TAG, "query from $timeMillis")
                    val entry = dropboxManager.getNextEntry("fragment", timeMillis)
                    log?.post {
                        log.append("${entry?.timeMillis}: ${entry?.getText(256)}\n")
                    }

                    if (entry != null) {
                        timeMillis = entry.timeMillis
                    }
                } while (entry != null)
            }
        }
        // }).start()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
