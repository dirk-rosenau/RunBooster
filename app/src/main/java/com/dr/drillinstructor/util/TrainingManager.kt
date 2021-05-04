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

    fun startTraining() {
        notificationHelper.showNotification()
        setLightMode()
    }

    fun stopTrainng() {
        alarmHelper.cancelAlarm()
        trainingStateProvider.setTrainingState(TrainingState.IDLE)
        notificationHelper.dismissNotification()
    }

    fun isTrainingStarted(): Boolean =
        preferenceRepository.getTrainingState() != TrainingState.IDLE

    private fun setLightMode() {
        Log.d("BroadcastReceiver", "enter LightMode")
        if (trainingStateProvider.getTrainingState() == TrainingState.HARD) {
            soundPlayer.playSound("outstanding.mp3")
            vibrationHelper.vibrateLightMode()
        } else {
            vibrationHelper.vibrateShort()
        }
        preferenceRepository.getLightModeDuration().toNextChangeTime().scheduleAsNextAlarm()
        trainingStateProvider.setTrainingState(TrainingState.LIGHT)
    }

    private fun setHardMode() {
        Log.d("BroadcastReceiver", "enter hardMode")
        preferenceRepository.getHardModeDuration().toNextChangeTime().scheduleAsNextAlarm()
        trainingStateProvider.setTrainingState(TrainingState.HARD)
        vibrationHelper.vibrateHardMode()
        soundPlayer.playSound("gogogo.mp3")
    }

    private fun Long.toNextChangeTime() = System.currentTimeMillis() + this

    private fun Long.scheduleAsNextAlarm(): Long {
        Log.d("TrainingManager", "next mode scheduled $this")
        preferenceRepository.setNextModeChangeTime(this)
        alarmHelper.setAlarm(this)
        return this
    }
}
