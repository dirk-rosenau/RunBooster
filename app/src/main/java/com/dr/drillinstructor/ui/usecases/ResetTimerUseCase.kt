package com.dr.drillinstructor.ui.usecases

import com.dr.drillinstructor.util.DEFAULT_HARD_MODE_DURATION
import com.dr.drillinstructor.util.DEFAULT_LIGHT_MODE_DURATION
import com.dr.drillinstructor.util.PreferenceRepository
import com.dr.drillinstructor.util.TrainingState

class ResetTimerUseCase(private val repository: PreferenceRepository) {
    fun execute(): Long {
        val nextChangeTime = when (repository.getTrainingState()) {
            TrainingState.HARD -> DEFAULT_HARD_MODE_DURATION + System.currentTimeMillis()
            TrainingState.LIGHT -> DEFAULT_LIGHT_MODE_DURATION + System.currentTimeMillis()
            else -> 0
        }
        repository.setNextModeChangeTime(nextChangeTime)
        return nextChangeTime
    }
}