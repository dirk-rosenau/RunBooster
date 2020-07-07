package com.dr.drillinstructor.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TrainingFragmentVM : ViewModel() {

    private val _timeStringData = MutableLiveData<String>()
    val timeStringData: LiveData<String> = _timeStringData

    private var dummyTime = System.currentTimeMillis() + 10000L

    fun startTimeer() {
        GlobalScope.launch {
            while (true) {
                _timeStringData.value = refreshTime()
            }
        }
    }

    private fun refreshTime(): String {
        val remainingTime = dummyTime - System.currentTimeMillis()

        if (remainingTime >= dummyTime) {
            dummyTime = System.currentTimeMillis() + 10000L
        }

        return String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(remainingTime),
            TimeUnit.MILLISECONDS.toSeconds(remainingTime),
            TimeUnit.MILLISECONDS.toMillis(remainingTime)
        )


    }

}
