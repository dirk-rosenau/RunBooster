package com.dr.drillinstructor.util

interface PreferenceRepository {
    fun getTrainingState(): TrainingState
    fun setTrainingState(trainingState: TrainingState)
    fun setHardModeDuration(duration: Long)
    fun getHardModeDuration(): Long
    fun getLightModeDuration(): Long
    fun setStartDelay(duration: Long)
    fun getStartDeleay(): Long
    fun setLightModeDuration(duration: Long)
    fun setRandomizeTimes(randomize: Boolean)
    fun getRandomizeTimes(): Boolean
}
