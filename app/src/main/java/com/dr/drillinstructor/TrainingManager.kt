package com.dr.drillinstructor

import android.util.Log
import com.dr.drillinstructor.wrapper.AlarmHelper

class TrainingManager(
    private val alarmHelper: AlarmHelper,
    private val trainingStateProvider: TrainingStateProvider
) {

    private val hardModeDuration: Long = 3 * 1000
    private val lightModeDuration: Long = 10 * 1000

    fun evaluateTrainingState() {
        when (trainingStateProvider.getTrainingState()) {
            TrainingState.LIGHT -> setHardMode()
            TrainingState.HARD -> setLightMode()
            TrainingState.IDLE -> cancelAlarm()
        }
    }

    private fun cancelAlarm() {
        alarmHelper.cancelAlarm()
    }

    private fun setLightMode() {
        Log.d("BroadcastReceiver", "enter LightMode")
        trainingStateProvider.setTrainingState(TrainingState.LIGHT)
        alarmHelper.setAlarm(lightModeDuration)
    }

    private fun setHardMode() {
        Log.d("BroadcastReceiver", "enter hardMode")
        trainingStateProvider.setTrainingState(TrainingState.HARD)
        alarmHelper.setAlarm(hardModeDuration)
    }
}