package com.example.go.Sign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.go.MainActivity
import com.example.go.Utils.FBAuth.auth
import com.example.go.Utils.FBAuth.getDisplayName
import com.example.go.Utils.FBAuth.setDisplayName
import com.example.go.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textButtonSignUp.setOnClickListener{
            val intent= Intent(this, SignUpActivity::class.java).apply {
                //액티비티 스택제거
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(intent)
        }

        binding.buttonSignIn.setOnClickListener{
            val userEmail = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (userEmail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            doLogin(userEmail, password) //로그인 시도
        }
    }

    private fun doLogin(userEmail: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) { //로그인 성공 시
                    startActivity(Intent(this, MainActivity::class.java)) //게시글 리스트 액티비티 시작
                    Log.d("user", getDisplayName())
                    finish()
                } else { //로그인 실패 시
                    Log.w("LoginActivity", "signInWithEmail", it.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
        }
}