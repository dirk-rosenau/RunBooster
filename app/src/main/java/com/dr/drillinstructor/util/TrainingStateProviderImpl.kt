package com.dr.drillinstructor.util

import androidx.lifecycle.MutableLiveData

class TrainingStateProviderImpl(private val preferenceRepository: PreferenceRepository) :
    TrainingStateProvider {

    override val liveTrainingState = MutableLiveData<TrainingState>()

    override fun getTrainingState(): TrainingState {
        return preferenceRepository.getTrainingState()
    }

    override fun setTrainingState(trainingState: TrainingState) {
        liveTrainingState.value = trainingState
        this.preferenceRepository.setTrainingState((trainingState))
    }
}
