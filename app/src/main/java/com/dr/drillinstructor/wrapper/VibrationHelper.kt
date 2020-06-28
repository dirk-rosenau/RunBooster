package com.dr.drillinstructor.wrapper

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

class VibrationHelper(private val context: Context) {
    fun vibrate() {
        val vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
        val vibrator =
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(vibrationEffect)
    }
}