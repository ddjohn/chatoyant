package com.avelon.chatoyant.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.home.databinding.SettingsHomeBinding

class HomeSettings : AppCompatActivity() {
    companion object {
        private val TAG = DLog.forTag(HomeFragment::class.java)
    }

    private var binding: SettingsHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        DLog.d(TAG, "onCreate()")
        super.onCreate(savedInstanceState)

        binding = SettingsHomeBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding!!.root)

        binding!!.save.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }
}
