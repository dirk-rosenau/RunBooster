package com.dr.drillinstructor.wrapper

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

class VibrationHelper(private val context: Context) {
    fun vibrateShort() {
        val vibrationEffect = VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrate(vibrationEffect)
    }

    fun vibrateHardMode() {
        val timings = longArrayOf(100, 150, 100, 150, 100, 150, 100, 150, 100, 150)
        val vibrationEffect = VibrationEffect.createWaveform(timings, -1)

        vibrate(vibrationEffect)
    }

    fun vibrateLightMode() {
        val timings = longArrayOf(100, 700, 100, 700, 100, 700)
        val vibrationEffect = VibrationEffect.createWaveform(timings, -1)

        vibrate(vibrationEffect)
    }

    private fun vibrate(vibrationEffect: VibrationEffect) {
        val vibrator =
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(vibrationEffect)
    }
}