package com.dr.drillinstructor.wrapper

import android.content.Context
import android.content.res.AssetManager
import android.media.MediaPlayer

class SoundPlayer(val assets: AssetManager, val mediaPlayer: MediaPlayer, val context: Context) {

    fun playSound(filename: String) {
        doIt(filename)
    }

    private fun doIt(filename: String) {
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

    /*  fun bla(filename: String) {
          val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
          val mAudioAttributes = AudioAttributes.Builder()
              .setUsage(AudioAttributes.USAGE_MEDIA)
              .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
              .build()
          val mAudioFocusRequest =
              AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                  .setAudioAttributes(mAudioAttributes)
                  .setAcceptsDelayedFocusGain(true)
                  .setOnAudioFocusChangeListener(this) // Need to implement listener
                  .build();
          val focusRequest = audioManager.requestAudioFocus(mAudioFocusRequest);
          when (focusRequest) {
              AudioManager.AUDIOFOCUS_REQUEST_FAILED -> {
                  Log.d("SoundPlayer", "no audio focus")
              }
              // don’t start playback
              AudioManager.AUDIOFOCUS_REQUEST_GRANTED -> {
                  doIt(filename)
              }
              // actually start playback
          }
      }

      override fun onAudioFocusChange(focusChange: Int) {
          //  nothing
      }*/
}
