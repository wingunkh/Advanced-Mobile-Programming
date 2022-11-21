package com.example.go.Post

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.example.go.databinding.ActivityPostWritingBinding
@Suppress("DEPRECATION")
class PostWritingActivity : AppCompatActivity() {
    private val user = Firebase.auth.currentUser
    private val userName = user?.uid.toString()

    private lateinit var binding : ActivityPostWritingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val posts = intent.getSerializableExtra("posts") as ArrayList<Post>

        binding.postWritingPostButton.setOnClickListener {
            val postTitle = binding.postWritingTitle.getText().toString()
            val postContent = binding.postWritingContent.getText().toString()
            val postUser = userName

            posts.add(Post(postTitle, postUser, postContent))
            val intent = Intent(this, PostListActivity::class.java)
            intent.putExtra("posts", posts)
            startActivity(intent)
        }
    }
}