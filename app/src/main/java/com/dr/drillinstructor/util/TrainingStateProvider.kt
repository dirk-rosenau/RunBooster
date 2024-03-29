package com.dr.drillinstructor.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface TrainingStateProvider {
    fun getTrainingState(): TrainingState
    fun setTrainingState(trainingState: TrainingState)

    // TODO state flow?
    val liveTrainingState: LiveData<TrainingState>
}