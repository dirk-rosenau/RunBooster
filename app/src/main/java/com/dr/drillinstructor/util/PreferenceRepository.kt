package com.dr.drillinstructor.util

interface PreferenceRepository {
    fun getTrainingState(): TrainingState
    fun setTrainingState(trainingState: TrainingState)
    fun setHardModeDuration(duration: Long)
    fun getHardModeDuration(): Long
    fun getLightModeDuration(): Long
    fun setStartDelay(duration: Long)
    fun getStartDelay(): Long
    fun setLightModeDuration(duration: Long)
    fun setRandomizeTimes(randomize: Boolean)
    fun getRandomizeTimes(): Boolean
    fun getNextModeChangeTime(): Long
    fun setNextModeChangeTime(time: Long)
    fun isPaused(): Boolean
    fun setIsPaused(paused: Boolean)
    fun setRemainingTimeBeforePause(time: Long)
    fun getRemainingTimeBeforePause(): Long
    fun setTrainingStartTime(time: Long)
    fun getTrainingStartTime(): Long
    fun setEnterPauseTime(time: Long)
    fun getEnterPauseTime(): Long
}
