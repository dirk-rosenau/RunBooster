package com.dr.drillinstructor.util

import android.content.SharedPreferences

class PreferenceRepositoryImpl(private val prefs: SharedPreferences) : PreferenceRepository {

    companion object {
        const val KEY_TRAINING_STATE = "trainingState"
        const val KEY_HARD_MODE_DURATION = "hardModeDuration"
        const val KEY_LIGHT_MODE_DURATION = "lightModeDuration"
        const val KEY_START_DELAY = "startDelay"
        const val KEY_RANDOMIZE_TIMES = "randomizeTimes"

    }

    override fun getTrainingState(): TrainingState {
        val trainingStateString = prefs.getString(KEY_TRAINING_STATE, null)
        return if (trainingStateString != null) {
            TrainingState.valueOf(trainingStateString)
        } else
            return TrainingState.IDLE
    }

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

    override fun getStartDeleay(): Long = prefs.getLong(KEY_START_DELAY, DEFAULT_START_DELAY)


    override fun setRandomizeTimes(randomize: Boolean) {
        prefs.edit().putBoolean(KEY_RANDOMIZE_TIMES, randomize).apply()
    }

    override fun getRandomizeTimes(): Boolean =
        prefs.getBoolean(KEY_RANDOMIZE_TIMES, DEFAULT_RANDOMIZE_KEYS)
}
