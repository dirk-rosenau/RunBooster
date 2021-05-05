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
    private val trainingManager: TrainingManager
) : ObservableViewModel() {

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time
    private val _mode = MutableLiveData<String>("Laufen")
    val mode: LiveData<String> = _mode

    private val _isTimeLabelVisible = MutableLiveData<Boolean>(true)
    val isTimeLabelVisible: LiveData<Boolean> = _isTimeLabelVisible

    private val _events = MutableLiveData<TrainingEvent>()
    val events: LiveData<TrainingEvent> = _events

    private var isPaused = repository.isPaused()

    private var nextChangeTime: Long = 0

    init {
        nextChangeTime = repository.getNextModeChangeTime()
        if (isPaused) {
            _time.postValue(getFormattedRemainingTime(repository.getRemainingTimeBeforePause()))
        }
        startTimerCoroutine()
        animateTimeLabelPauseMode()
    }

    private fun startTimerCoroutine() {
        viewModelScope.launch(Dispatchers.IO) {
            runTimer()
        }
    }

    fun onStopButtonClicked() {
        _events.value = StopClicked
    }

    fun onReplayButtonClicked() {
        stopPauseModeIfNecessary()
        trainingManager.resetTraining()
    }

    fun onForwardButtonClicked() {
        stopPauseModeIfNecessary()
        trainingManager.toggleTrainingMode()
    }

    fun onPauseButtonClicked() {
        isPaused = !isPaused
        trainingManager.togglePause(nextChangeTime - System.currentTimeMillis())
        animateTimeLabelPauseMode()
    }

    private fun stopPauseModeIfNecessary() {
        if (isPaused) {
            onPauseButtonClicked()
        }
    }

    private fun changeTrainingStateDisplay(state: TrainingState?) {
        if (state == TrainingState.HARD) {
            _mode.postValue("Sprint!")
        } else {
            _mode.postValue("Laufen")
        }
    }

    private fun animateTimeLabelPauseMode() {
        viewModelScope.launch {
            while (isPaused) {
                _isTimeLabelVisible.value = _isTimeLabelVisible.value?.not()
                delay(600)
            }
            _isTimeLabelVisible.value = true
        }

    }

    private suspend fun runTimer() {
        while (true) {
            val remainingTime: Long = nextChangeTime - System.currentTimeMillis()
            if (remainingTime >= 0 && !isPaused) {
                _time.postValue(getFormattedRemainingTime(remainingTime))
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