//고급 모바일 프로그래밍 팀 프로젝트

package com.example.go.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.databinding.ActivityPostListBinding

// test

class PostListActivity : AppCompatActivity() {
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
    private lateinit var binding : ActivityPostListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
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