package com.example.go.Sign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.go.Post.PostListActivity
import com.example.go.databinding.ActivitySignupBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textButtonSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java)) //로그인 액티비티로 되돌아간다.
        }

        binding.buttonSignUp.setOnClickListener {
            val userEmail = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirm = binding.signupConfirmPassword.text.toString()

            if(comparePassword(password, confirm))
                doSignUp(userEmail, password)
        }
    }

    private fun doSignUp(userEmail: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "SignUp Success!.", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(this, LoginActivity::class.java)
                    ) //로그인 액티비티로 되돌아간다.
                    finish()
                } else {
                    Log.w("SignUpActivity", "signUpWithEmail", it.exception)
                    Toast.makeText(this, "SignUp failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun comparePassword(password: String, confirm: String): Boolean {
        return if (password == confirm)
            true
        else {
            Toast.makeText(this, "Please check the password again!.", Toast.LENGTH_SHORT).show()
            false
        }
    }
}
