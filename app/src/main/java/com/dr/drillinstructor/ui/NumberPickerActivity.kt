package com.dr.drillinstructor.ui

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.dr.drillinstructor.R
import com.dr.drillinstructor.util.*
import kotlinx.android.synthetic.main.activity_number_picker.*
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class NumberPickerActivity : WearableActivity() {

    private val preferenceRepository: PreferenceRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_picker)

        setAmbientEnabled()

        initNumberPicker()
    }

    override fun onPause() {
        val min = picker.value
        val sec = picker2.value

        var result = min * 60 * 1000 + sec * 1000
        if (result < MIN_TIME) {
            result = MIN_TIME // at least 1 seconds
        }


        // huge smell
        when (intent.getStringExtra(BUNDLE_START_MODE_VALUE)) {
            BUNDLE_LIGHT_MODE -> {
                preferenceRepository.setLightModeDuration(result.toLong())
            }
            BUNDLE_HARD_MODE -> {
                preferenceRepository.setHardModeDuration(result.toLong())
            }
            BUNDLE_STARTIME -> {
                preferenceRepository.setStartDelay(result * 1000 * 60L)
            }
        }
        super.onPause()
    }

    private fun initNumberPicker() {
        picker.minValue = 0
        picker.maxValue = 59

        picker2.minValue = 0
        picker2.maxValue = 59

        val duration = when (intent.getStringExtra(BUNDLE_START_MODE_VALUE)) {
            BUNDLE_LIGHT_MODE ->
                preferenceRepository.getLightModeDuration()
            BUNDLE_HARD_MODE -> preferenceRepository.getHardModeDuration()
            else -> 0
        }

        val min = TimeUnit.MILLISECONDS.toMinutes(duration)
        val sec = duration / 1000 % 60

        picker.value = min.toInt()
        picker2.value = sec.toInt()
    }

    companion object {
        const val MIN_TIME = 5000
    }
}
