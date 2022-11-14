//고급 모바일 프로그래밍 팀 프로젝트

package com.example.go

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.go.databinding.ActivityLoginBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}