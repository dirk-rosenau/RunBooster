package com.dr.drillinstructor.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dr.drillinstructor.ui.events.MainEvent
import com.dr.drillinstructor.ui.events.OpenSettings
import com.dr.drillinstructor.ui.events.StartTraining
import com.dr.drillinstructor.ui.events.StopTraining

class MainActivityViewModel : ViewModel() {
    private val _mainEventLiveData = MutableLiveData<MainEvent>()
    val mainEventLiveData: LiveData<MainEvent> = _mainEventLiveData

    fun settingsButtonClicked() {
        _mainEventLiveData.value = OpenSettings
    }

    fun playButtonClicked() {
        _mainEventLiveData.value = StartTraining
    }

    fun stopButtonClicked() {
        _mainEventLiveData.value = StopTraining
    }
}
