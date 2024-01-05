package com.dr.drillinstructor.wrapper

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.wear.ongoing.OngoingActivity
import androidx.wear.ongoing.Status
import com.dr.drillinstructor.R
import com.dr.drillinstructor.ui.MainActivity

class NotificationHelper(private val context: Context) {
    private val channelId = "Drill Instructor"
    private val notificationId = 9874

    init {
        createNotificationChannel()
    }

    fun showNotification(nextChangeTime: Long) {
        val intent = Intent(context, MainActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_sprint)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.notification_run_mode_active))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)

        //val statusText = "#time#"
        val statusText = "Run Booster"

        val ongoingActivityStatus = Status.Builder()
            // Sets the text used across various surfaces.
            .addTemplate(statusText)
            //.addPart("time", Status.StopwatchPart(nextChangeTime))
            .build()

        val ongoingActivity =
            OngoingActivity.Builder(
                context, notificationId, builder
            )
                // Sets the animated icon that will appear on the watch face in
                // active mode.
                // If it isn't set, the watch face will use the static icon in
                // active mode.
                .setAnimatedIcon(R.drawable.ic_sprint)
                // Sets the icon that will appear on the watch face in ambient mode.
                // Falls back to Notification's smallIcon if not set.
                // If neither is set, an Exception is thrown.
                .setStaticIcon(R.drawable.ic_sprint)
                // Sets the tap/touch event so users can re-enter your app from the
                // other surfaces.
                // Falls back to Notification's contentIntent if not set.
                // If neither is set, an Exception is thrown.
                .setTouchIntent(pendingIntent)
                // Here, sets the text used for the Ongoing Activity (more
                // options are available for timers and stopwatches).
                .setStatus(ongoingActivityStatus)
                .build()

        ongoingActivity.apply(context)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(notificationId, builder.build())
            }
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
