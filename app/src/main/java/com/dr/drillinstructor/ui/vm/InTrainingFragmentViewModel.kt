package com.dr.drillinstructor.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dr.drillinstructor.ui.events.StopClicked
import com.dr.drillinstructor.ui.events.TrainingEvent
import com.dr.drillinstructor.ui.usecases.ResetTimerUseCase
import com.dr.drillinstructor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class InTrainingFragmentViewModel(
    private val repository: PreferenceRepository,
    private val trainingManager: TrainingManager,
    private val resetTimerUseCase: ResetTimerUseCase,
    private val trainingStateProvider: TrainingStateProvider
) : ObservableViewModel() {

    val time = MutableLiveData<String>()
    val mode = MutableLiveData<String>("Laufen")


    private val _events = MutableLiveData<TrainingEvent>()
    val events: LiveData<TrainingEvent> = _events

    private var nextChangeTime: Long = 0

    private fun changeTrainingStateDisplay(state: TrainingState?) {
        if (state == TrainingState.HARD) {
            mode.postValue("Sprint!")
        } else {
            mode.postValue("Laufen")
        }
    }

    fun onStopButtonClicked() {
        _events.value = StopClicked
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
        //  nextChangeTime = trainingManager.resetTimer()
        nextChangeTime = repository.getNextModeChangeTime()
    }

    fun startCoroutine() {
        viewModelScope.launch(Dispatchers.IO) {
            runTimer()
        }
    }

    private suspend fun runTimer() {
        while (true) {
            val remainingTime: Long = nextChangeTime - System.currentTimeMillis()
            if (remainingTime >= 0) {
                time.postValue(getFormattedRemainingTime(remainingTime))
            }
            delay(10)
        }
    }

    private fun getFormattedRemainingTime(time: Long) = String.format(
        "%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(time),
        time / 1000 % 60,
        time / 10 % 100
    )

    fun setTrainingState(state: TrainingState?) {
        changeTrainingStateDisplay(state)
        nextChangeTime = repository.getNextModeChangeTime()
        Log.d(
            "intrainingViewModel", "$state, nextChangeTime: ${
                getFormattedRemainingTime(nextChangeTime - System.currentTimeMillis())
            }"
        )
    }
}