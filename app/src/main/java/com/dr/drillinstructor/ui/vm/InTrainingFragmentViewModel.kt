package com.dr.drillinstructor.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dr.drillinstructor.ui.usecases.ResetTimerUseCase
import com.dr.drillinstructor.util.ObservableViewModel
import com.dr.drillinstructor.util.PreferenceRepository
import com.dr.drillinstructor.util.TrainingManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class InTrainingFragmentViewModel(
    private val repository: PreferenceRepository,
    private val trainingManager: TrainingManager,
    private val resetTimerUseCase: ResetTimerUseCase
) : ObservableViewModel() {

    val time = MutableLiveData<String>()
    val mode = MutableLiveData<String>("Laufen")

    private val _stopClicked: MutableLiveData<Boolean> = MutableLiveData(false)
    val stopClicked: LiveData<Boolean> = _stopClicked

    private var remainingTime: Long = 0

    private var nextChangeTime: Long = 0

    fun onStopButtonClicked() {
        _stopClicked.value = true
    }

    fun onReplayButtonClicked() {
        resetTime()
    }

    fun onForwardButtonClicked() {
        // TODO time + 10 seconds
    }

    fun onPauseButtonClicked() {
        // TODO stop timer and wait
    }

    init {
        nextChangeTime = repository.getNextModeChangeTime()
        //resetTime()
        startCoroutine()
    }

    private fun resetTime() {
        nextChangeTime = trainingManager.resetTimer()
    }

    fun startCoroutine() {
        viewModelScope.launch(Dispatchers.IO) {
            runTimer()
        }
    }

    private suspend fun runTimer() {
        while (true) {
            remainingTime = nextChangeTime - System.currentTimeMillis()
            if (remainingTime <= 0) {
                resetTime()
                // eigentlich müsste hier das training change zeug angefeuert werden,
                // und nur wenn die app im hintergrund ist, sollte der alarm gestellt werden
            }
            time.postValue(getFormattedRemainingTime())
            delay(10)
        }
    }

    private fun getFormattedRemainingTime() = String.format(
        "%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
        remainingTime / 1000 % 60,
        remainingTime / 10 % 100
    )
}