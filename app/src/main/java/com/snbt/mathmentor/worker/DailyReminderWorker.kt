package com.snbt.mathmentor.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar
import java.util.concurrent.TimeUnit

@HiltWorker
class DailyReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "snbt_daily_reminder"
        val channel = NotificationChannel(channelId, "Pengingat Belajar Harian", NotificationManager.IMPORTANCE_HIGH)
        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("🎯 Waktunya Belajar!")
            .setContentText("Jangan putus streak-mu! Selesaikan materi hari ini sebelum malam 💪")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify(1001, notification)
    }

    companion object {
        const val WORK_NAME = "daily_reminder_work"

        fun schedule(context: Context) {
            val now = Calendar.getInstance()
            val target = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 7)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                if (before(now)) add(Calendar.DAY_OF_MONTH, 1)
            }
            val delay = target.timeInMillis - now.timeInMillis

            val request = PeriodicWorkRequestBuilder<DailyReminderWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setConstraints(Constraints.Builder().build())
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
