package com.dr.drillinstructor.wrapper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.dr.drillinstructor.receiver.DrillCallBroadcastReceiver

class AlarmHelper(private val context: Context) {

    private val requestID = -6667
    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    fun setAlarm(time: Long) {
        val pendingIntent = getPendingIntent(0)
        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent)
        }

        alarmManager?.set(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    fun cancelAlarm() {
        val pendingIntent = getPendingIntent(PendingIntent.FLAG_NO_CREATE)
        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent)
        }
    }

    fun isAlarmSet() = getPendingIntent(PendingIntent.FLAG_NO_CREATE) != null

    private fun getPendingIntent(flags: Int): PendingIntent? {
        val intent = Intent(context, DrillCallBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(
            context, requestID, intent,
            flags
        )
    }
}
