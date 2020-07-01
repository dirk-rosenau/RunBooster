package com.dr.drillinstructor.util

class TrainingStateProviderImpl(private val preferenceRepository: PreferenceRepository) :
    TrainingStateProvider {

    override fun getTrainingState(): TrainingState {
        return preferenceRepository.getTrainingState()
    }

    override fun setTrainingState(trainingState: TrainingState) {
        this.preferenceRepository.setTrainingState((trainingState))
    }
}
