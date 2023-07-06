package com.dicoding.habitapp.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dicoding.habitapp.R
import com.dicoding.habitapp.ui.list.HabitListActivity
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HABIT_TITLE
import com.dicoding.habitapp.utils.NOTIFICATION_CHANNEL_ID
import com.dicoding.habitapp.utils.NOTIF_UNIQUE_WORK

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val habitId = inputData.getInt(HABIT_ID, 0)
    private val habitTitle = inputData.getString(HABIT_TITLE)

    override fun doWork(): Result {
        //TODO 12 : If notification preference on, show notification with pending intent
        if (shouldShowNotification()) {
            showNotification()
        }
        return Result.success()
    }

    private fun shouldShowNotification(): Boolean {
        val prefManager = androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        return prefManager.getBoolean(applicationContext.getString(R.string.pref_key_notify), false)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification() {
        val intent = Intent(applicationContext, HabitListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        val mNotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = buildNotificationBuilder(pendingIntent)

        createNotificationChannel(mNotificationManager)

        val notification = mBuilder.build()
        mNotificationManager.notify(habitId, notification)
    }

    private fun buildNotificationBuilder(pendingIntent: PendingIntent): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications)
            .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_notifications))
            .setContentTitle(habitTitle)
            .setContentText(applicationContext.getString(R.string.notify_content))
            .setAutoCancel(true)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIF_UNIQUE_WORK,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        notificationManager.createNotificationChannel(channel)
    }
}
