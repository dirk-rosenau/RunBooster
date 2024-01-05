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
        setLightMode()
        val nextChangeTime = preferenceRepository.getNextModeChangeTime()
        notificationHelper.showNotification(nextChangeTime)
        preferenceRepository.setIsPaused(false)
    }

    fun stopTrainng() {
        alarmHelper.cancelAlarm()
        trainingStateProvider.setTrainingState(TrainingState.IDLE)
        notificationHelper.dismissNotification()
    }

    fun resetTraining() {
        vibrationHelper.vibrateShort()
        alarmHelper.cancelAlarm()
        val trainingState = trainingStateProvider.getTrainingState()
        val nextDuration = if (trainingState == TrainingState.HARD) {
            preferenceRepository.getHardModeDuration()
        } else {
            preferenceRepository.getLightModeDuration()
        }
        nextDuration.toNextChangeTime().scheduleAsNextAlarm()
        trainingStateProvider.setTrainingState(trainingState) // refresh
    }

    fun toggleTrainingMode() {
        vibrationHelper.vibrateShort()
        alarmHelper.cancelAlarm()
        evaluateTrainingState()
    }

    fun togglePause(remainingTime: Long) {
        vibrationHelper.vibrateShort()
        val isPaused = preferenceRepository.isPaused()
        preferenceRepository.setIsPaused(!isPaused)
        if (!isPaused) {
            alarmHelper.cancelAlarm()
            // save actual remaining time
            preferenceRepository.setRemainingTimeBeforePause(remainingTime)
        } else {
            val newRemainingTime = preferenceRepository.getRemainingTimeBeforePause()
            newRemainingTime.toNextChangeTime().scheduleAsNextAlarm()
            trainingStateProvider.setTrainingState(trainingStateProvider.getTrainingState()) // refresh vm
        }
    }

    fun isTrainingStarted(): Boolean = preferenceRepository.getTrainingState() != TrainingState.IDLE


    private fun setLightMode() {
        Log.d("BroadcastReceiver", "enter LightMode")
        if (trainingStateProvider.getTrainingState() == TrainingState.HARD) {
            if (preferenceRepository.isSoundEnabled()) {
                playSoundIfEnabled("outstanding2.mp3")
            }
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
        playSoundIfEnabled("gogogo2.mp3")
    }

    private fun playSoundIfEnabled(filename: String) {
        if (preferenceRepository.isSoundEnabled()) {
            soundPlayer.playSound(filename)
        }
    }

    private fun Long.toNextChangeTime() = System.currentTimeMillis() + this

    private fun Long.scheduleAsNextAlarm(): Long {
        Log.d("TrainingManager", "next mode scheduled $this")
        preferenceRepository.setNextModeChangeTime(this)
        alarmHelper.setAlarm(this)
        return this
    }
}
