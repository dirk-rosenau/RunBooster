package com.dr.drillinstructor.util

import com.dr.drillinstructor.util.TrainingState

interface TrainingStateProvider {
    fun getTrainingState(): TrainingState
    fun setTrainingState(trainingState: TrainingState)
}