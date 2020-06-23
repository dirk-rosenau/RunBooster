package com.dr.drillinstructor

import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.wearable.activity.WearableActivity
import com.dr.drillinstructor.R
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
        if (playState) {
            play_button.setImageResource(R.drawable.ic_stop)
        } else {
            play_button.setImageResource(R.drawable.ic_play_arrow)
        }
        vibrate()
    }

    private fun vibrate() {
        val vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
        val vibrator =
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        vibrator.vibrate(vibrationEffect)
    }
}
