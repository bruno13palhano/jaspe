package com.bruno13palhano.jaspe.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bruno13palhano.jaspe.MainActivity
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.Notification
import com.bruno13palhano.repository.RepositoryFactory
import com.example.network.service.NetworkFactory

class NotificationWork(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val offerNotificationNetwork = NetworkFactory().createOfferNotificationNetwork()
    private val notificationRepository = RepositoryFactory(context).createNotificationRepository()

    override suspend fun doWork(): Result {

        offerNotificationNetwork.getOfferNotification().collect {
            notificationRepository.insertNotification(it)
            initOfferNotification(it)
        }

        return Result.success()
    }

    private fun initOfferNotification(offerNotification: Notification) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext,
            0, intent, PendingIntent.FLAG_IMMUTABLE)

        createNotificationChannel()

        val builderNotification = NotificationCompat.Builder(applicationContext,
                "OFFERS_NOTIFICATION")
            .setSmallIcon(R.drawable.ic_baseline_email_24)
            .setContentTitle(offerNotification.title)
            .setContentText(offerNotification.description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with (NotificationManagerCompat.from(applicationContext)) {
            notify(1, builderNotification.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "shopdani"
            val descriptionText = "notification shopdani"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("OFFERS_NOTIFICATION", name, importance)
                    .apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                applicationContext
                    .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}