package com.dr.drillinstructor

import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.wearable.activity.WearableActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : WearableActivity() {

    private val soundPlayer: SoundPlayer by inject()
    private val alarmHelper: AlarmHelper by inject()

    private var playState = false;
    private val SPRINT_TIME = 30 * 1000 // secounds
    private val PAUSE_TIME = 4 * 60 * 1000// minutes
    private var runState = RunState.IDLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        play_button.setOnClickListener {
            togglePlay()
        }
    }

    // 1. runstate von idle zu run
    // 2. start sound spielen
    // 3. alarm stellen für jogg time
    // 4. state zu sprint
    // 5. gogogo spielenn
    // 6. alarm stellen für sprint zeit
    // 7. outstanding spielen
    // zurück zu 3
    private fun togglePlay() {
        playState = !playState
        if (!playState) {
            handleStopPressed()
        } else {
            handlePlayPressed()
        }
        vibrate()
    }

    private fun handlePlayPressed() {
        play_button.setImageResource(R.drawable.ic_stop)
        soundPlayer.playSound("gogogo.mp3")
        alarmHelper.setAlam(5 * 1000)
        Toast.makeText(this, "Started", Toast.LENGTH_LONG).show()
    }

    private fun handleStopPressed() {
        play_button.setImageResource(R.drawable.ic_play_arrow)
        soundPlayer.playSound("outstanding.mp3")
    }

    private fun startJoggingMode() {
        runState = RunState.JOGGING
        // play start sound

    }

    private fun vibrate() {
        val vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
        val vibrator =
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        vibrator.vibrate(vibrationEffect)
    }
}
