package com.dr.drillinstructor

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.dr.drillinstructor.receiver.DrillCallBroadcastReceiver

class AlarmHelper(private val context: Context) {

    private val requestID = -6667
    fun setAlam(time: Long) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, DrillCallBroadcastReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context, requestID, intent,
                0
            )
        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent)
        }

        alarmManager?.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + time,
            pendingIntent
        )
    }
}
