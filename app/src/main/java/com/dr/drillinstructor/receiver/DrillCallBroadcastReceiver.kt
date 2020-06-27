package com.dr.drillinstructor.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class DrillCallBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "GoGogo", Toast.LENGTH_LONG).show()
        Log.d("Broadcast", "Received")
    }
}
