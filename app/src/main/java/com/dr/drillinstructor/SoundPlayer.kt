package com.dr.drillinstructor

import android.content.res.AssetManager
import android.media.MediaPlayer

class SoundPlayer(val assets: AssetManager, val mediaPlayer: MediaPlayer) {

    fun playSound(filename: String) {
        try {
            val descriptor = assets.openFd(filename)
            mediaPlayer.setDataSource(
                descriptor.fileDescriptor,
                descriptor.startOffset,
                descriptor.length
            )
            descriptor.close()
            mediaPlayer.prepare()
            mediaPlayer.setVolume(1f, 1f)
            mediaPlayer.isLooping = false
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}