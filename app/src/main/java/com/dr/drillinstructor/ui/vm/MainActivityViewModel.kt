package com.dr.drillinstructor.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dr.drillinstructor.ui.events.*

class MainActivityViewModel : ViewModel() {
    val mainEventLiveData = MutableLiveData<MainEvent>()

    val ambient = MutableLiveData<AmbientEvent>()


    fun settingsButtonClicked() {
        mainEventLiveData.value = OpenSettings
    }

    fun playButtonClicked() {
        mainEventLiveData.value = StartTraining
    }

    fun stopButtonClicked() {
        mainEventLiveData.value = StopTraining
    }
}
