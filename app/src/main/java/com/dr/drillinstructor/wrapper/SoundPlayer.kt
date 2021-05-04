package com.dr.drillinstructor.wrapper

import android.content.Context
import android.content.res.AssetManager
import android.media.MediaPlayer

class SoundPlayer(val assets: AssetManager, val mediaPlayer: MediaPlayer, val context: Context) {

    fun playSound(filename: String) {
        try {
            mediaPlayer.reset()
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
