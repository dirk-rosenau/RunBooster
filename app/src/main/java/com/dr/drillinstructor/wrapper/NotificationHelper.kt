package com.dr.drillinstructor.wrapper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dr.drillinstructor.R
import com.dr.drillinstructor.ui.MainActivity

class NotificationHelper(private val context: Context) {
    private val channelId = "Drill Instructor"
    private val notificationId = 9874

    init {
        createNotificationChannel()
    }

    fun showNotification() {
        val intent = Intent(context, MainActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_directions_run)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.notification_run_mode_active))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        getNotificationManager().createNotificationChannel(channel)
    }

    private fun getNotificationManager() =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun dismissNotification() {
        getNotificationManager().cancel(notificationId)
    }
}
