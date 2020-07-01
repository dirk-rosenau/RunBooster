package com.dr.drillinstructor.ui

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.dr.drillinstructor.R
import com.dr.drillinstructor.util.TrainingManager
import com.dr.drillinstructor.wrapper.VibrationHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : WearableActivity() {

    private val vibrationHelper: VibrationHelper by inject()
    private val trainingManager: TrainingManager by inject()

    private var playButtonState = false

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
        playButtonState = !playButtonState
        if (!playButtonState) {
            handleStopPressed()
        } else {
            handlePlayPressed()
        }
        vibrationHelper.vibrateShort()
    }

    private fun handlePlayPressed() {
        play_button.setImageResource(R.drawable.ic_stop)
        trainingManager.setLightMode()
    }

    private fun handleStopPressed() {
        play_button.setImageResource(R.drawable.ic_play_arrow)
        // play sound giving up / outstanding je nachdem
        // soundPlayer.playSound("outstanding.mp3")
        trainingManager.stopTrainng()
    }
}
