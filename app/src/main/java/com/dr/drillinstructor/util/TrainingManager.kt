package com.dr.drillinstructor.util

import android.util.Log
import com.dr.drillinstructor.wrapper.AlarmHelper
import com.dr.drillinstructor.wrapper.NotificationHelper
import com.dr.drillinstructor.wrapper.SoundPlayer
import com.dr.drillinstructor.wrapper.VibrationHelper

class TrainingManager(
    private val alarmHelper: AlarmHelper,
    private val trainingStateProvider: TrainingStateProvider,
    private val soundPlayer: SoundPlayer,
    private val vibrationHelper: VibrationHelper,
    private val preferenceRepository: PreferenceRepository,
    private val notificationHelper: NotificationHelper
) {
    fun evaluateTrainingState() {
        when (trainingStateProvider.getTrainingState()) {
            TrainingState.LIGHT -> setHardMode()
            TrainingState.HARD -> setLightMode()
            TrainingState.IDLE -> stopTrainng()
        }
    }

    fun stopTrainng() {
        alarmHelper.cancelAlarm()
        trainingStateProvider.setTrainingState(TrainingState.IDLE)
        notificationHelper.dismissNotification()
    }

    fun isTrainingStarted(): Boolean =
        preferenceRepository.getTrainingState() != TrainingState.IDLE

    fun setLightMode() {
        Log.d("BroadcastReceiver", "enter LightMode")
        if (trainingStateProvider.getTrainingState() == TrainingState.HARD) {
            soundPlayer.playSound("outstanding.mp3")
            vibrationHelper.vibrateLightMode()
        } else {
            vibrationHelper.vibrateShort()
        }
        trainingStateProvider.setTrainingState(TrainingState.LIGHT)
        alarmHelper.setAlarm(preferenceRepository.getLightModeDuration())

        notificationHelper.showNotification()
    }

    fun setHardMode() {
        Log.d("BroadcastReceiver", "enter hardMode")
        trainingStateProvider.setTrainingState(TrainingState.HARD)
        alarmHelper.setAlarm(preferenceRepository.getHardModeDuration())
        vibrationHelper.vibrateHardMode()
        soundPlayer.playSound("gogogo.mp3")
    }
}
