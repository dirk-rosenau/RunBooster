package com.dr.drillinstructor.ui.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class AmbientViewModel(application: Application) : AndroidViewModel(application) {

    private val _ambient = MutableLiveData<Boolean>(false)
    val ambient: LiveData<Boolean> = _ambient

    open fun enterAmbient() {
        _ambient.value = true
    }

    open fun exitAmbient() {
        _ambient.value = false
    }

    abstract fun updateAmbient()
}