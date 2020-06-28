package com.dr.drillinstructor.util

import com.dr.drillinstructor.util.TrainingState
import com.dr.drillinstructor.util.TrainingStateProvider

class TrainingStateProviderImpl :
    TrainingStateProvider {
    var traininState: TrainingState = TrainingState.IDLE

    override fun getTrainingState(): TrainingState {
        return traininState
    }

    override fun setTrainingState(trainingState: TrainingState) {
        this.traininState = trainingState
    }
}