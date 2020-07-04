package com.dr.drillinstructor.ui

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.dr.drillinstructor.R
import com.dr.drillinstructor.util.*
import kotlinx.android.synthetic.main.activity_number_picker.*
import org.koin.android.ext.android.inject

class NumberPickerActivity : WearableActivity() {

    private val preferenceRepository: PreferenceRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_picker)

        setAmbientEnabled()

        initNumberPicker()
    }

    override fun onPause() {
        val result = picker.value
        // huge smell
        when (intent.getStringExtra(BUNDLE_START_MODE_VALUE)) {
            BUNDLE_LIGHT_MODE -> {
                preferenceRepository.setLightModeDuration(result * 1000 * 60L)
            }
            BUNDLE_HARD_MODE -> {
                preferenceRepository.setHardModeDuration(result * 1000L)
            }
            BUNDLE_STARTIME -> {
                preferenceRepository.setStartDelay(result * 1000 * 60L)
            }
        }
        super.onPause()
    }

    private fun initNumberPicker() {
        picker.minValue = 1
        picker.maxValue = 35

        picker.value = when (intent.getStringExtra(BUNDLE_START_MODE_VALUE)) {
            BUNDLE_LIGHT_MODE -> preferenceRepository.getLightModeDuration().toMinuteInt()
            BUNDLE_HARD_MODE -> preferenceRepository.getHardModeDuration().toMSecondInt()
            BUNDLE_STARTIME -> preferenceRepository.getStartDelay().toMinuteInt()
            else -> 1
        }
    }

    private fun Int.fromSecond() = (this * 1000).toLong()
    private fun Long.toMSecondInt() = (this / 1000).toInt()
    private fun Int.fromMinute() = (this * 1000 * 60).toLong()
    private fun Long.toMinuteInt() = (this / 60 / 1000).toInt()
}
