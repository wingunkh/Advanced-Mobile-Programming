package com.example.go.Sign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.go.Model.ImagePost
import com.example.go.Utils.FBRef
import com.example.go.Utils.FBAuth
import com.example.go.databinding.ActivitySignupBinding
import com.example.go.Model.UserModel
import com.example.go.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textButtonSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java)) //로그인 액티비티로 되돌아간다.
        }

        binding.buttonSignUp.setOnClickListener {
            val nickname = binding.signupNickname.text.toString()
            val userEmail = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirm = binding.signupConfirmPassword.text.toString()

            if (userEmail.isEmpty() || password.isEmpty() || confirm.isEmpty())
                Toast.makeText(this, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()

            else if(comparePassword(password, confirm))
                doSignUp(nickname, userEmail, password)
        }
    }

    private fun doSignUp(nickname: String, userEmail: String, password: String) {
        FBAuth.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val uid = it.result.user?.uid
                    FBRef.userRef
                        .child(uid!!).setValue(UserModel(uid,"",password,userEmail,nickname))
                    Toast.makeText(this, "SignUp Success!.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //회원가입하면 뒤에있는 액티비티 없애기
                    startActivity(intent)
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
