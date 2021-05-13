package com.dr.drillinstructor.tracking

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseTracker(private val analytics: FirebaseAnalytics) {

    fun trackStartTraining(time: Long) {
        val bundle = Bundle()
        bundle.putLong(FirebaseAnalytics.Param.START_DATE, time)
        analytics.logEvent(START_TRAINING, bundle)
    }

    fun trackStopTraining(duration: Long) {
        val bundle = Bundle()
        bundle.putLong(DURATION, duration)
        analytics.logEvent(STOP_TRAINING, bundle)
    }

    fun trackPauseTraining(duration: Long) {
        val bundle = Bundle()
        bundle.putLong(DURATION, duration)
        analytics.logEvent(PAUSE_TRAINING, bundle)
    }

    fun trackContinueTraining(duration: Long) {
        val bundle = Bundle()
        bundle.putLong(DURATION, duration)
        analytics.logEvent(CONTINUE_TRAINING, bundle)
    }

    fun trackResetSprint() {
        analytics.logEvent(RESET_SPRINT, null)
    }


    fun trackResetJogging() {
        analytics.logEvent(RESET_JOGGING, null)
    }


    fun trackForwardJogging() {
        analytics.logEvent(FORWARD_JOGGING, null)
    }


    fun trackForwardSprint() {
        analytics.logEvent(FORWARD_SPRINT, null)
    }

    fun setSprintTime(time: Long) {
        analytics.setUserProperty(SPRINT_TIME, time.toString())
    }

    fun setJoggingTime(time: Long) {
        analytics.setUserProperty(JOGGING_TIME, time.toString())
    }

    companion object {
        const val START_TRAINING = "start_training"
        const val PAUSE_TRAINING = "pause_training"
        const val CONTINUE_TRAINING = "continue_training"
        const val STOP_TRAINING = "stop_training"
        const val RESET_SPRINT = "reset_sprint"
        const val RESET_JOGGING = "reset_jogging"
        const val FORWARD_SPRINT = "forward_sprint"
        const val FORWARD_JOGGING = "forward_jogging"
        const val DURATION = "duration"
        const val SPRINT_TIME = "sprint_time"
        const val JOGGING_TIME = "jogging_time"
    }
}