package com.example.snakegame

import android.media.MediaPlayer
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class MediaPlayerHelper(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun play(resId: Int, loop: Boolean = true) {
        stop()
        mediaPlayer = MediaPlayer.create(context, resId).apply {
            isLooping = loop
            start()
        }
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun resume() {
        mediaPlayer?.start()
    }

}

@Composable
fun rememberMediaPlayer(): MediaPlayerHelper {
    val context = LocalContext.current
    return remember { MediaPlayerHelper(context) }
}