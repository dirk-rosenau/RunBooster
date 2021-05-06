package com.dr.drillinstructor.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TrainingStateProviderImpl(private val preferenceRepository: PreferenceRepository) :
    TrainingStateProvider {

    val _liveTrainingState = MutableLiveData<TrainingState>()
    override val liveTrainingState: LiveData<TrainingState> = _liveTrainingState

    override fun getTrainingState(): TrainingState {
        return preferenceRepository.getTrainingState()
    }

    override fun setTrainingState(trainingState: TrainingState) {
        _liveTrainingState.value = trainingState
        this.preferenceRepository.setTrainingState((trainingState))
    }
}
