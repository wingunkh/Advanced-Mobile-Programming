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

        val post = intent.getSerializableExtra("post") as Post
        binding.writePostTitleText.text = post.title
        binding.writePostUsername.text = post.user
        binding.writePostContentText.text = post.content
    }
}