package com.dr.drillinstructor.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dr.drillinstructor.util.TrainingManager
import org.koin.java.KoinJavaComponent.inject

class DrillCallBroadcastReceiver : BroadcastReceiver() {

    private val trainingManager: TrainingManager by inject(
        TrainingManager::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Broadcast", "Received")
        trainingManager.evaluateTrainingState()
    }
}
