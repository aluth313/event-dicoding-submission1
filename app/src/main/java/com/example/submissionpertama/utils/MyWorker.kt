package com.example.submissionpertama.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.submissionpertama.R
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.SyncHttpClient
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    companion object{
        private val TAG = MyWorker::class.java.simpleName
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "daily remainder"
    }

    private var resultStatus: Result? = null

    override fun doWork(): Result {
        return getOneEvent()
    }

    private fun getOneEvent(): Result {
        Looper.prepare()
        val sharedPref = applicationContext.getSharedPreferences("remainder_setting", Context.MODE_PRIVATE)
        val isRemainderSettingActive = sharedPref.getBoolean("remainder", false)
        if (!isRemainderSettingActive){
            Log.d(TAG, "Remainder is Disable, Skipping Notification")
            return Result.success()
        }

        val client = SyncHttpClient()
        val url = "https://event-api.dicoding.dev/events?active=1&limit=1"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                try {
                    val responseObject = JSONObject(result)
                    if (responseObject.getJSONArray("listEvents").length() > 0){
                        val beginTime: String = responseObject.getJSONArray("listEvents").getJSONObject(0).getString("beginTime")
                        val name: String = responseObject.getJSONArray("listEvents").getJSONObject(0).getString("name")
                        val message = "($beginTime) $name"
                        if (isRemainderSettingActive){
                            showNotification("Event terdekat", message)
                        }
                    }
                    resultStatus = Result.success()
                } catch (e: Exception){
                    val msgError = "onFailure: ${e.message}"
                    Log.e(TAG, msgError)
                    showNotification("Gagal mengambil data event", msgError)
                    resultStatus = Result.failure()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val msgError = "onFailure: ${error?.message}"
                Log.e(TAG, msgError)
                showNotification("Gagal mengambil data event", msgError)
                resultStatus = Result.failure()
            }
        })

        return resultStatus as Result
    }

    private fun showNotification(title: String, description: String?){
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }
}