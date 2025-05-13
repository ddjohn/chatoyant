package com.avelon.chatoyant.exoplayer

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.analytics.AnalyticsListener
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.exoplayer.databinding.FragmentExoplayerBinding

class ExoPlayerFragment : Fragment() {
    companion object {
        private val TAG = DLog.forTag(ExoPlayerFragment::class.java)
    }

    private var _binding: FragmentExoplayerBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentExoplayerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val playerView = binding.playerView

        // DAJO
        val player = ExoPlayer.Builder(requireContext()).build()
        player.addListener(
            object : ExoPlayerListener() {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    Log.e(TAG, "onIsPlayingChanged(): $isPlaying")
                    if (isPlaying) {
                        playerView.postDelayed(this::getCurrentPlayerPosition, 100)
                    }
                }

                private fun getCurrentPlayerPosition() {
                    DLog.d(TAG, "current pos: ${player.currentPosition}")
                    if (player.isPlaying) {
                        playerView.postDelayed(this::getCurrentPlayerPosition, 100)
                    }
                }
            },
        )

        player.addAnalyticsListener(
            object : AnalyticsListener {
            },
        )

        playerView.player = player

        val videoUri = Uri.parse("https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4")
        val mediaItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
