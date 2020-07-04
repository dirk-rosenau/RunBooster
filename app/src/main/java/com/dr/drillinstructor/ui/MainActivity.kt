package com.dr.drillinstructor.ui

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import com.dr.drillinstructor.R
import com.dr.drillinstructor.util.TrainingManager
import com.dr.drillinstructor.wrapper.VibrationHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : WearableActivity() {

    private val vibrationHelper: VibrationHelper by inject()
    private val trainingManager: TrainingManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        initPlayButton()
        initButtonClickListeners()
    }

    private fun initButtonClickListeners() {
        play_button.setOnClickListener {
            togglePlay()
        }

        settings_button.setOnClickListener {
            openSettings()
        }
    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun initPlayButton() {
        if (trainingManager.isTrainingStarted()) {
            play_button.setImageResource(R.drawable.ic_stop)
        }
    }

    private fun togglePlay() {
        if (trainingManager.isTrainingStarted()) {
            handleStopPressed()
        } else {
            handlePlayPressed()
        }
        vibrationHelper.vibrateShort()
    }

    private fun handlePlayPressed() {
        play_button.setImageResource(R.drawable.ic_stop)
        settings_button.visibility = View.INVISIBLE
        trainingManager.setLightMode()
    }

    private fun handleStopPressed() {
        play_button.setImageResource(R.drawable.ic_play_arrow)
        settings_button.visibility = View.VISIBLE

        trainingManager.stopTrainng()
    }
}
