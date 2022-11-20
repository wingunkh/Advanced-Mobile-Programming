package com.example.go.Post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.go.databinding.ActivityPostBinding

@Suppress("DEPRECATION")
class PostActivity : AppCompatActivity(){
    private lateinit var binding : ActivityPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = intent.getParcelableExtra<Post>("post")
        binding.postTitleText.text = post?.title
        binding.postUserText.text = post?.user
        binding.postContentText.text = post?.content
    }
}