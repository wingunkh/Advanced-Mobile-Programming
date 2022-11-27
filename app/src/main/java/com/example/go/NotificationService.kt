package com.example.go

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.go.Utils.FBRef
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class NotificationService : Service() {

    private fun createNotification() {
        val builder = NotificationCompat.Builder(this, "default")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("Foreground Service")
        builder.setContentText("포그라운드 서비스")
        builder.color = Color.RED
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pendingIntent) // 알림 클릭 시 이동

        // 알림 표시
        val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "default",
                    "기본 채널",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        FBRef.postRef.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.value.toString().substring(1,snapshot.value.toString().length-1).split(", ")
                val title = message[4].split("=")[1]
                val user = message[5].split("=")[1]
                showNotification(title, user)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private val myNotificationId = 1
    private val channelId = "default"

    fun showNotification(title: String, user: String) {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(user + "님이 글을 작성하였습니다.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(this)
            .notify(myNotificationId, builder.build())
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int):Int {
        Log.e(TAG, Thread.currentThread().name)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0
            createNotification()
        }


    }
}