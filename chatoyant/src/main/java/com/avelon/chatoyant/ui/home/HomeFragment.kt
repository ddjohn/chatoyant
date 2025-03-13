package com.avelon.chatoyant.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.databinding.FragmentHomeBinding
import com.google.zxing.BarcodeFormat

class HomeFragment : Fragment() {

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

        //DAJO
        val barcodeEncoder = com.journeyapps.barcodescanner.BarcodeEncoder()
        val bitMatrix = barcodeEncoder.encode("Hello World", BarcodeFormat.QR_CODE, 400, 400)
        val bitmap = barcodeEncoder.createBitmap(bitMatrix)

        val imageView = binding.imageView;
        imageView.setImageBitmap(bitmap)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}