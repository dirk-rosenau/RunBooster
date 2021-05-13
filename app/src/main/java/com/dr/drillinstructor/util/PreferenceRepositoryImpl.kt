package com.dr.drillinstructor.util

import android.content.SharedPreferences

class PreferenceRepositoryImpl(private val prefs: SharedPreferences) : PreferenceRepository {

    companion object {
        const val KEY_TRAINING_STATE = "trainingState"
        const val KEY_HARD_MODE_DURATION = "hardModeDuration"
        const val KEY_LIGHT_MODE_DURATION = "lightModeDuration"
        const val KEY_START_DELAY = "startDelay"
        const val KEY_RANDOMIZE_TIMES = "randomizeTimes"
        const val NEXT_MODE_CHANGE = "nextModeChange"
        const val KEY_PAUSED = "paused"
        const val KEY_REMAINING_TIME_BEFORE_PAUSE = "remainingTimeBeforePause"
        const val START_TIME = "startTime"
        const val PAUSE_ENTER_TIME = "pauseEnterTime"

    }

    override fun getTrainingState(): TrainingState {
        val trainingStateString = prefs.getString(KEY_TRAINING_STATE, null)
        return if (trainingStateString != null) {
            TrainingState.valueOf(trainingStateString)
        } else
            return TrainingState.IDLE
    }

    override fun isPaused(): Boolean = prefs.getBoolean(KEY_PAUSED, false)

    override fun setIsPaused(paused: Boolean) = prefs.edit().putBoolean(KEY_PAUSED, paused).apply()

    override fun setRemainingTimeBeforePause(time: Long) {
        prefs.edit().putLong(KEY_REMAINING_TIME_BEFORE_PAUSE, time).apply()
    }

    override fun getRemainingTimeBeforePause(): Long =
        prefs.getLong(KEY_REMAINING_TIME_BEFORE_PAUSE, 0L)

    override fun setTrainingStartTime(time: Long) {
        prefs.edit().putLong(START_TIME, time).apply()
    }

    override fun getTrainingStartTime(): Long = prefs.getLong(START_TIME, 0)

    override fun setEnterPauseTime(time: Long) {
        prefs.edit().putLong(PAUSE_ENTER_TIME, time).apply()
    }

    override fun getEnterPauseTime(): Long = prefs.getLong(PAUSE_ENTER_TIME, 0)


    override fun setTrainingState(trainingState: TrainingState) {
        prefs.edit().putString(KEY_TRAINING_STATE, trainingState.name).apply()
    }

    override fun setHardModeDuration(duration: Long) {
        prefs.edit().putLong(KEY_HARD_MODE_DURATION, duration).apply()
    }

    override fun getHardModeDuration(): Long =
        prefs.getLong(KEY_HARD_MODE_DURATION, DEFAULT_HARD_MODE_DURATION)


    override fun setLightModeDuration(duration: Long) {
        prefs.edit().putLong(KEY_LIGHT_MODE_DURATION, duration).apply()
    }

    override fun getLightModeDuration(): Long =
        prefs.getLong(KEY_LIGHT_MODE_DURATION, DEFAULT_LIGHT_MODE_DURATION)


    override fun setStartDelay(duration: Long) {
        prefs.edit().putLong(KEY_START_DELAY, duration).apply()
    }

    override fun getStartDelay(): Long = prefs.getLong(KEY_START_DELAY, DEFAULT_START_DELAY)


    override fun setRandomizeTimes(randomize: Boolean) {
        prefs.edit().putBoolean(KEY_RANDOMIZE_TIMES, randomize).apply()
    }

    override fun getRandomizeTimes(): Boolean =
        prefs.getBoolean(KEY_RANDOMIZE_TIMES, DEFAULT_RANDOMIZE_KEYS)


    override fun getNextModeChangeTime(): Long =
        prefs.getLong(NEXT_MODE_CHANGE, DEFAULT_NEXT_MODE_CHANGE_TIME)


    override fun setNextModeChangeTime(time: Long) {
        prefs.edit().putLong(NEXT_MODE_CHANGE, time).apply()
    }
}
