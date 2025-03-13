package com.avelon.chatoyant.ui.exoplayer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.Listener
import androidx.media3.exoplayer.ExoPlayer
import com.avelon.chatoyant.databinding.FragmentExoplayerBinding

class ExoplayerFragment : Fragment() {

    private var _binding: FragmentExoplayerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExoplayerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val playerView = binding.playerView

        // DAJO
        val player = ExoPlayer.Builder(requireContext()).build()
        /*player.addListener(Listener() {

        })*/

        playerView.player = player

        val videoUri = Uri.parse("https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4");
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