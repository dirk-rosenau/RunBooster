package com.dr.drillinstructor.util

interface PreferenceRepository {
    fun getTrainingState(): TrainingState
    fun setTrainingState(trainingState: TrainingState)
}
