package com.dr.drillinstructor.util

import android.util.Log
import com.dr.drillinstructor.tracking.FirebaseTracker
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
    private val notificationHelper: NotificationHelper,
    private val tracker: FirebaseTracker
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
        preferenceRepository.setIsPaused(false)
        setLightMode()
        preferenceRepository.setTrainingStartTime(System.currentTimeMillis())
        tracker.trackStartTraining(System.currentTimeMillis())
    }

    fun stopTrainng() {
        alarmHelper.cancelAlarm()
        trainingStateProvider.setTrainingState(TrainingState.IDLE)
        notificationHelper.dismissNotification()
        tracker.trackStopTraining(getDurationFromStartTime())
    }

    fun resetTraining() {
        vibrationHelper.vibrateShort()
        alarmHelper.cancelAlarm()
        val trainingState = trainingStateProvider.getTrainingState()
        val nextDuration = if (trainingState == TrainingState.HARD) {
            tracker.trackResetSprint()
            preferenceRepository.getHardModeDuration()
        } else {
            tracker.trackResetJogging()
            preferenceRepository.getLightModeDuration()
        }
        nextDuration.toNextChangeTime().scheduleAsNextAlarm()
        trainingStateProvider.setTrainingState(trainingState) // refresh
    }

    fun toggleTrainingMode() {
        vibrationHelper.vibrateShort()
        if (trainingStateProvider.getTrainingState() == TrainingState.HARD) {
            tracker.trackForwardSprint()
        } else {
            tracker.trackForwardJogging()
        }
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
            preferenceRepository.setEnterPauseTime(System.currentTimeMillis())
            tracker.trackPauseTraining(getDurationFromStartTime())
        } else {
            val newRemainingTime = preferenceRepository.getRemainingTimeBeforePause()
            newRemainingTime.toNextChangeTime().scheduleAsNextAlarm()
            trainingStateProvider.setTrainingState(trainingStateProvider.getTrainingState()) // refresh vm
            tracker.trackContinueTraining(System.currentTimeMillis() - preferenceRepository.getEnterPauseTime())
        }
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

    private fun getDurationFromStartTime() =
        System.currentTimeMillis() - preferenceRepository.getTrainingStartTime()

    private fun Long.toNextChangeTime() = System.currentTimeMillis() + this

    private fun Long.scheduleAsNextAlarm(): Long {
        Log.d("TrainingManager", "next mode scheduled $this")
        preferenceRepository.setNextModeChangeTime(this)
        alarmHelper.setAlarm(this)
        return this
    }
}
