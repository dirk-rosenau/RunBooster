package com.dr.drillinstructor.ui.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

class MainFragmentViewModel(application: Application) : AmbientViewModel(application) {

    private val _playButtonClicked: MutableLiveData<Boolean> = MutableLiveData()
    val playButtonClicked: LiveData<Boolean> = _playButtonClicked

    private val _settingsButtonClicked: MutableLiveData<Boolean> = MutableLiveData()
    val settingsButtonClicked: LiveData<Boolean> = _settingsButtonClicked

    private val _time: MutableLiveData<String> = MutableLiveData()
    val time: LiveData<String> = _time


    fun onPlayButtonClicked() {
        _playButtonClicked.value = true
    }

    fun onSettingsButtonClicked() {
        _settingsButtonClicked.value = true
    }

    override fun enterAmbient() {
        super.enterAmbient()
        updateTime()
    }

    override fun updateAmbient() {
        updateTime()
    }

    private fun updateTime() {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.ROOT)
        _time.value = timeFormat.format(Date())
    }

    companion object {
        const val TAG = "MainFragmentViewModel"
    }
}