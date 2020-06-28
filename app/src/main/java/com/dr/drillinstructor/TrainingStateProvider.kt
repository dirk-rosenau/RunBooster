package com.dr.drillinstructor

interface TrainingStateProvider {
    fun getTrainingState(): TrainingState
    fun setTrainingState(trainingState: TrainingState)
}