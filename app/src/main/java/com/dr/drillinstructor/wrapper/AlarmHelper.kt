package com.dr.drillinstructor.wrapper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import com.dr.drillinstructor.receiver.DrillCallBroadcastReceiver

class AlarmHelper(private val context: Context) {

    private val requestID = -6667
    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    fun setAlarm(time: Long) {
        val pendingIntent = getPendingIntent(PendingIntent.FLAG_IMMUTABLE)
        alarmManager?.let { alarmManager ->
            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent)
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        time,
                        pendingIntent
                    )
                } else {
                    context.startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                }
            }
        }
    }

    fun cancelAlarm() {
        val pendingIntent =
            getPendingIntent(PendingIntent.FLAG_NO_CREATE)
        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent)
        }
    }

    fun isAlarmSet() =
        getPendingIntent(PendingIntent.FLAG_NO_CREATE) != null

    private fun getPendingIntent(flags: Int): PendingIntent? {
        val intent = Intent(context, DrillCallBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(
            context, requestID, intent,
            flags or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
