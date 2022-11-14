package com.example.go

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.go.databinding.ActivityMainBinding
import com.example.go.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity(){
    private lateinit var binding : ActivityPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = intent.getSerializableExtra("post") as Post
        binding.postContentText.text = post.content
        binding.postTitleText.text = post.title
        binding.postUserText.text = post.user

    }
}