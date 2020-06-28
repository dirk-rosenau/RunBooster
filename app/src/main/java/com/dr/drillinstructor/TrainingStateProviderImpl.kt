package com.dr.drillinstructor

class TrainingStateProviderImpl : TrainingStateProvider {
    var traininState: TrainingState = TrainingState.IDLE

    override fun getTrainingState(): TrainingState {
        return traininState
    }

    override fun setTrainingState(trainingState: TrainingState) {
        this.traininState = trainingState
    }
}