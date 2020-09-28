package com.dr.drillinstructor.ui.vm

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.dr.drillinstructor.BR
import com.dr.drillinstructor.util.ObservableViewModel
import com.dr.drillinstructor.util.PreferenceRepository
import com.dr.drillinstructor.util.TrainingManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class InTrainingFragmentViewModel(
    private val repository: PreferenceRepository,
    private val trainingManager: TrainingManager
) : ObservableViewModel() {

    @get:Bindable
    var time: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.time)
        }

    var remainingTime: Long = 0

    var nextChangeTime: Long = 0

    fun onStopButtonClicked() {

    }

    init {
        nextChangeTime = System.currentTimeMillis() + (10 * 1000)
        startCoroutine()
    }

    private fun initTime() {
        //  nextChangeTime = repository.getNextModeChangeTime() - System.currentTimeMillis()
        nextChangeTime = System.currentTimeMillis() + (10 * 1000)
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
                initTime()
            }
            //Log.d("VM", "remaining: $remainingTime")
            calculateTimes()
            delay(10)
        }
    }

    private fun calculateTimes() {
        val theTime = String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(remainingTime),
            TimeUnit.MILLISECONDS.toSeconds(remainingTime),
            remainingTime / 10 % 100
        )


        time = theTime

        /*  remainingMilliseconds = remainingTime.toInt()
          remainingSeconds = (remainingTime / 1000).toInt()
          remainingHours = (remainingTime / 1000 / 60).toInt()
          Log.d("VM", "hours: $remainingHours")
          Log.d("VM", "minutes: $remainingSeconds")
          Log.d("VM", "$remainingMilliseconds")*/
    }


}