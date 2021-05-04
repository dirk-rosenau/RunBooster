package com.dr.drillinstructor.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFragmentViewModel : ViewModel() {

    private val _playButtonClicked: MutableLiveData<Boolean> = MutableLiveData()
    val playButtonClicked: LiveData<Boolean> = _playButtonClicked

    private val _settingsButtonClicked: MutableLiveData<Boolean> = MutableLiveData()
    val settingsButtonClicked: LiveData<Boolean> = _settingsButtonClicked

    fun onPlayButtonClicked() {
        _playButtonClicked.value = true
    }

    fun onSettingsButtonClicked() {
        _settingsButtonClicked.value = true
    }

    companion object {
        const val TAG = "MainFragmentViewModel"
    }
}