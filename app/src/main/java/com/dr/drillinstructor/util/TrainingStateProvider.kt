package com.dr.drillinstructor.util

import androidx.lifecycle.MutableLiveData
import com.dr.drillinstructor.util.TrainingState

interface TrainingStateProvider {
    fun getTrainingState(): TrainingState
    fun setTrainingState(trainingState: TrainingState)
    val liveTrainingState: MutableLiveData<TrainingState>
}