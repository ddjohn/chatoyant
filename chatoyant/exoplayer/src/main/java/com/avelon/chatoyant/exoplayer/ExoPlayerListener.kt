package com.avelon.chatoyant.exoplayer

import android.util.Log
import androidx.media3.common.DeviceInfo
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.common.VideoSize
import com.avelon.chatoyant.crosscutting.DLog

open class ExoPlayerListener : Player.Listener {
    companion object {
        private val TAG = DLog.forTag(ExoPlayerListener::class.java)
    }

    override fun onEvents(
        player: Player,
        events: Player.Events,
    ) {
        DLog.d(TAG, "onEvents()")
        super.onEvents(player, events)

        for (i in 0 until events.size()) {
            val event = events.get(i)
            when (event) {
                // 0
                Player.EVENT_TIMELINE_CHANGED -> Log.i(TAG, "EVENT_TIMELINE_CHANGED")
                // 1
                Player.EVENT_MEDIA_ITEM_TRANSITION -> Log.i(TAG, "EVENT_MEDIA_ITEM_TRANSITION")
                // 2
                Player.EVENT_TRACKS_CHANGED -> Log.i(TAG, "EVENT_TRACKS_CHANGED")
                // 3
                Player.EVENT_IS_LOADING_CHANGED -> Log.i(TAG, "EVENT_IS_LOADING_CHANGED")
                // 4
                Player.EVENT_PLAYBACK_STATE_CHANGED -> Log.i(TAG, "EVENT_PLAYBACK_STATE_CHANGED")
                // 5
                Player.EVENT_PLAY_WHEN_READY_CHANGED -> Log.i(TAG, "EVENT_PLAY_WHEN_READY_CHANGED")
                // 13
                Player.EVENT_AVAILABLE_COMMANDS_CHANGED -> Log.i(TAG, "EVENT_AVAILABLE_COMMANDS_CHANGED")

                Player.EVENT_IS_PLAYING_CHANGED -> Log.i(TAG, "EVENT_IS_PLAYING_CHANGED")
                Player.EVENT_PLAYBACK_SUPPRESSION_REASON_CHANGED -> Log.i(TAG, "EVENT_PLAYBACK_SUPPRESSION_REASON_CHANGED")
                Player.EVENT_SURFACE_SIZE_CHANGED -> Log.i(TAG, "EVENT_SURFACE_SIZE_CHANGED")
                Player.EVENT_VIDEO_SIZE_CHANGED -> Log.i(TAG, "EVENT_VIDEO_SIZE_CHANGED")
                Player.EVENT_RENDERED_FIRST_FRAME -> Log.i(TAG, "EVENT_RENDERED_FIRST_FRAME")

                else -> Log.i(TAG, "Unknown event $event")
            }
        }
    }

    override fun onDeviceInfoChanged(deviceInfo: DeviceInfo) {
        Log.i(TAG, "onDeviceInfoChanged(): ${deviceInfo.routingControllerId}")
        super.onDeviceInfoChanged(deviceInfo)
    }

    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.artist}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.mediaType}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.writer}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.genre}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.albumArtist}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.albumTitle}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.artworkData}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.artworkUri}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.composer}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.compilation}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.conductor}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.description}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.discNumber}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.displayTitle}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.title}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.station}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.subtitle}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.trackNumber}")
        Log.i(TAG, "onMediaMetadataChanged(): ${mediaMetadata.userRating}")
        super.onMediaMetadataChanged(mediaMetadata)
    }

    override fun onPlayerError(error: PlaybackException) {
        DLog.e(TAG, "error= $error")
    }

    override fun onTracksChanged(tracks: Tracks) {
        Log.i(TAG, "onTracksChanged(): $tracks")
        super.onTracksChanged(tracks)
    }

    override fun onVideoSizeChanged(videoSize: VideoSize) {
        Log.i(TAG, "onVideoSizeChanged(): ${videoSize.width}x${videoSize.height}x${videoSize.pixelWidthHeightRatio}")
        super.onVideoSizeChanged(videoSize)
    }
}
