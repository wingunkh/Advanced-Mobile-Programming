package com.example.go

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val posts = listOf(
        Post("hi1", "geonhee", "hello"),
        Post("hi2", "geonhee", "hello"),
        Post("hi3", "geonhee", "hello"),
        Post("hi4", "geonhee", "hello"),
        Post("hi5", "geonhee", "hello"),
        Post("hi6", "geonhee", "hello"),
        Post("hi7", "geonhee", "hello"),
        Post("hi8", "geonhee", "hello")
    )
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        binding.imageListButton.setOnClickListener {
            val intent = Intent(this, ImageListActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initViews() {
        binding.postList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = PostAdapter(posts)
        binding.postList.adapter = adapter
        val intent = Intent(this, PostActivity::class.java)
        adapter.itemClick = object : PostAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                intent.putExtra("post", posts[position])
                startActivity(intent)
            }
        }


    }
}