package com.example.go.ImagePost

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.*
import com.example.go.Post.PostListActivity
import com.example.go.databinding.ActivityImageListBinding

class ImageListActivity : AppCompatActivity(){

    private val imagePosts = listOf(
        ImagePost(R.drawable.cake,"hi1", "geonhee", "hello"),
        ImagePost(R.drawable.muhan,"hi2", "geonhee", "hello"),
        ImagePost( R.drawable.cake, "hi3", "geonhee", "hello"),
        ImagePost(R.drawable.cake,"hi4", "geonhee", "hello"),
        ImagePost(R.drawable.muhan,"hi5", "geonhee", "hello"),
        ImagePost(R.drawable.cake,"hi6", "geonhee", "hello"),
        ImagePost(R.drawable.muhan,"hi7", "geonhee", "hello"),
        ImagePost(R.drawable.cake,"hi8", "geonhee", "hello")
    )

    private lateinit var binding : ActivityImageListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.postListButton.setOnClickListener {
            val intent = Intent(this, PostListActivity::class.java)
            startActivity(intent)
        }
        initViews()
    }

    private fun initViews() {
        binding.postList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = ImagePostAdapter(imagePosts)
        binding.postList.adapter = adapter
        val intent = Intent(this, ImagePostActivity::class.java)
        adapter.itemClick = object : ImagePostAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                intent.putExtra("post", imagePosts[position])
                startActivity(intent)
            }
        }

    }
}