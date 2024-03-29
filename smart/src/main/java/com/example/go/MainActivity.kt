package com.example.go

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.go.Utils.FBAuth.getDisplayName
import com.example.go.Utils.FBAuth.setDisplayName
import com.example.go.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : Activity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            val userEmail = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (userEmail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            doLogin(userEmail, password) //로그인 시도
            Log.d("tlqkf","rotlqkf")

            //////////////////////////////////
            // 현재 기기에 설정된 쓰기 권한을 가져오기 위한 변수

            var writePermission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

            // 현재 기기에 설정된 읽기 권한을 가져오기 위한 변수
            var readPermission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )

            // 읽기 권한과 쓰기 권한에 대해서 설정이 되어있지 않다면
            if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
                // 읽기, 쓰기 권한을 요청합니다.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    1
                )
            }

            requestSinglePermission(Manifest.permission.POST_NOTIFICATIONS)

            val serviceIntent = Intent(this@MainActivity, NotificationService::class.java)
            startService(serviceIntent)
        }
    }

    private fun doLogin(userEmail: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) { //로그인 성공 시
                    startActivity(Intent(this, SmartActivity::class.java)) //게시글 리스트 액티비티 시작
                    setDisplayName()
                    Log.d("user", getDisplayName())
                    finish()
                } else { //로그인 실패 시
                    Log.w("LoginActivity", "signInWithEmail", it.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun requestSinglePermission(permission: String) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return
    }

    private val channelId = "default"

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        val channel = NotificationChannel(
            channelId, "default channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "description text of this channel."
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private var myNotificationId = 1
        get() = field++

    fun showNotification(title: String, user: String) {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(user + "님이 글을 작성하였습니다.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(this)
            .notify(myNotificationId, builder.build())
    }

    fun showNotificationBigText() {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("제목")
            .setContentText("내용")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("큰 내용")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(this)
            .notify(myNotificationId, builder.build())
    }
}