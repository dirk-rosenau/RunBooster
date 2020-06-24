package com.dr.drillinstructor

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.wearable.activity.WearableActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : WearableActivity() {


    private var playState = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        play_button.setOnClickListener {
            togglePlay()
        }
    }

    private fun togglePlay() {
        playState = !playState
        if (!playState) {
            play_button.setImageResource(R.drawable.ic_play_arrow)
            playSound("outstanding.mp3")
        } else {
            play_button.setImageResource(R.drawable.ic_stop)
            playSound("gogogo.mp3")
        }
        vibrate()
    }

    private fun playSound(filename: String) {
        try {
            val m = MediaPlayer()
            val descriptor = assets.openFd(filename)
            m.setDataSource(
                descriptor.fileDescriptor,
                descriptor.startOffset,
                descriptor.length
            )
            descriptor.close()
            m.prepare()
            m.setVolume(1f, 1f)
            m.isLooping = false
            m.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun vibrate() {
        val vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
        val vibrator =
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        vibrator.vibrate(vibrationEffect)
    }
}
