package com.dr.drillinstructor.util

import android.util.Log
import com.dr.drillinstructor.wrapper.AlarmHelper
import com.dr.drillinstructor.wrapper.SoundPlayer

class TrainingManager(
    private val alarmHelper: AlarmHelper,
    private val trainingStateProvider: TrainingStateProvider
    , private val soundPlayer: SoundPlayer
) {

    private val hardModeDuration: Long = 3 * 1000
    private val lightModeDuration: Long = 10 * 1000

    fun evaluateTrainingState() {
        when (trainingStateProvider.getTrainingState()) {
            TrainingState.LIGHT -> setHardMode()
            TrainingState.HARD -> setLightMode()
            TrainingState.IDLE -> stopTrainng()
        }
    }


    fun stopTrainng() {
        alarmHelper.cancelAlarm()
        trainingStateProvider.setTrainingState(TrainingState.LIGHT)
    }

    fun setLightMode() {
        Log.d("BroadcastReceiver", "enter LightMode")
        if (trainingStateProvider.getTrainingState() == TrainingState.HARD) {
            soundPlayer.playSound("outstanding.mp3")
        }
        trainingStateProvider.setTrainingState(TrainingState.LIGHT)
        alarmHelper.setAlarm(lightModeDuration)
    }

    fun setHardMode() {
        Log.d("BroadcastReceiver", "enter hardMode")
        trainingStateProvider.setTrainingState(TrainingState.HARD)
        alarmHelper.setAlarm(hardModeDuration)
        soundPlayer.playSound("gogogo.mp3")
    }
}