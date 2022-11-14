package com.example.go

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.go.databinding.ActivityImagePostBinding

class ImagePostActivity : AppCompatActivity(){
    private lateinit var binding : ActivityImagePostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = intent.getSerializableExtra("post") as ImagePost
        binding.imagePostUserText.text = post.user
        binding.imagePostContentText.text = post.content
        binding.imagePostTitleText.text = post.title
        binding.postImageView.setImageResource(post.imgSrc)
    }
}