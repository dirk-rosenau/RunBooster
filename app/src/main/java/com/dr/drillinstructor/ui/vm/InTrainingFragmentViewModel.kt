package com.dr.drillinstructor.ui.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dr.drillinstructor.R
import com.dr.drillinstructor.ui.events.StopClicked
import com.dr.drillinstructor.ui.events.TrainingEvent
import com.dr.drillinstructor.util.PreferenceRepository
import com.dr.drillinstructor.util.TrainingManager
import com.dr.drillinstructor.util.TrainingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class InTrainingFragmentViewModel(
    application: Application,
    private val repository: PreferenceRepository,
    private val trainingManager: TrainingManager
) : AndroidViewModel(application) {

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time
    private val _mode = MutableLiveData<String>()
    val mode: LiveData<String> = _mode

    private val _isTimeLabelVisiblePause = MutableLiveData<Boolean>(true)
    val isTimeLabelVisiblePause: LiveData<Boolean> = _isTimeLabelVisiblePause

    private val _isTimeLabelVisibleResetOrForward = MutableLiveData<Boolean>(true)
    val isTimeLabelVisibleResetOrForward: LiveData<Boolean> = _isTimeLabelVisibleResetOrForward

    private val _events = MutableLiveData<TrainingEvent>()
    val events: LiveData<TrainingEvent> = _events

    private var isPaused = repository.isPaused()

    private var nextChangeTime: Long = 0

    init {
        nextChangeTime = repository.getNextModeChangeTime()
        setTrainingModeLabel(R.string.jogging_mode)
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
        animateTimeLabelResetOrForward()
    }

    fun onForwardButtonClicked() {
        stopPauseModeIfNecessary()
        trainingManager.toggleTrainingMode()
        animateTimeLabelResetOrForward()
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
        val resId = if (state == TrainingState.HARD) {
            R.string.sprint_mode
        } else {
            R.string.jogging_mode
        }
        setTrainingModeLabel(resId)
    }

    private fun setTrainingModeLabel(resId: Int) {
        _mode.postValue(getApplication<Application>().getString(resId))
    }

    private fun animateTimeLabelPauseMode() {
        viewModelScope.launch {
            while (isPaused) {
                _isTimeLabelVisiblePause.value = _isTimeLabelVisiblePause.value?.not()
                delay(600)
            }
            _isTimeLabelVisiblePause.value = true
        }
    }

    private fun animateTimeLabelResetOrForward() {
        viewModelScope.launch {
            _isTimeLabelVisibleResetOrForward.value = false
            delay(200)
            _isTimeLabelVisibleResetOrForward.value = true
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
