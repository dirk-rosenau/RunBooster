package com.dr.drillinstructor.receiver

import android.app.AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dr.drillinstructor.util.TrainingManager
import org.koin.java.KoinJavaComponent.inject

class DrillCallBroadcastReceiver : BroadcastReceiver() {

    private val trainingManager: TrainingManager by inject(
        TrainingManager::class.java
    )

    override fun onReceive(context: Context?, intent: Intent) {
        Log.d("Broadcast", "Received")
        when (intent.action) {
            ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED -> {

            }

            "runbooster_alarm" -> {
                trainingManager.evaluateTrainingState()
            }
        }
    }
}
