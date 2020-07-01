package com.dr.drillinstructor.util

import android.content.SharedPreferences

class PreferenceRepositoryImpl(private val prefs: SharedPreferences) : PreferenceRepository {

    companion object {
        const val KEY_TRAINING_STATE = "trainingState"
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


}
