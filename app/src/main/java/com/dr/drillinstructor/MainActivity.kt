package com.dr.drillinstructor

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.dr.drillinstructor.wrapper.AlarmHelper
import com.dr.drillinstructor.wrapper.VibrationHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : WearableActivity() {

    private val soundPlayer: SoundPlayer by inject()
    private val alarmHelper: AlarmHelper by inject()
    private val vibrationHelper: VibrationHelper by inject()
    private val trainingStateProvider: TrainingStateProvider by inject()

    private var playButtonState = false;
    private val SPRINT_TIME = 30 * 1000 // secounds
    private val PAUSE_TIME = 4 * 60 * 1000// minutes

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
        playButtonState = !playButtonState
        if (!playButtonState) {
            handleStopPressed()
        } else {
            handlePlayPressed()
        }
        vibrationHelper.vibrate()
    }

    private fun handlePlayPressed() {
        play_button.setImageResource(R.drawable.ic_stop)

        // TODO auslagern in extra manager klasse oder alarm helper umfunktionieren
        trainingStateProvider.setTrainingState(TrainingState.LIGHT)
        alarmHelper.setAlarm(5 * 1000)
    }

    private fun handleStopPressed() {
        play_button.setImageResource(R.drawable.ic_play_arrow)
        // play sound giving up / outstanding
        // soundPlayer.playSound("outstanding.mp3")
        alarmHelper.cancelAlarm()
    }

    private fun startJoggingMode() {
        trainingStateProvider.setTrainingState(TrainingState.LIGHT)
    }
}
