package com.avelon.chatoyant.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.home.databinding.FragmentHomeBinding
import com.google.zxing.BarcodeFormat
import kotlin.concurrent.thread

class HomeFragment : Fragment() {
    companion object {
        private val TAG = DLog.forTag(HomeFragment::class.java)
    }

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        DLog.d(TAG, "onCreateView()")

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // DAJO
        val barcodeEncoder = com.journeyapps.barcodescanner.BarcodeEncoder()
        val bitMatrix = barcodeEncoder.encode("Hello World", BarcodeFormat.QR_CODE, 400, 400)
        val bitmap = barcodeEncoder.createBitmap(bitMatrix)

        val imageView = binding.imageView
        imageView.setImageBitmap(bitmap)

        // WebServer(4444).run()
        // WebServer(4445).run()

        thread {
            // WebClient("localhost", 4445).run()
        }

        return root
    }

    override fun onDestroyView() {
        DLog.d(TAG, "onDestroyView()")
        super.onDestroyView()

        _binding = null
    }
}
