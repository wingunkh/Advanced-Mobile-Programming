//고급 모바일 프로그래밍 팀 프로젝트

package com.example.go.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.databinding.ActivityPostListBinding

// test

@Suppress("DEPRECATION")
class PostListActivity : AppCompatActivity() {
    private var posts = ArrayList<Post>()

    private lateinit var binding : ActivityPostListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        posts.add(Post("hi1", "geonhee", "hello"))
        posts.add(Post("hi2", "geonhee", "hello"))
        posts.add(Post("hi3", "geonhee", "hello"))
        posts.add(Post("hi4", "geonhee", "hello"))
        posts.add(Post("hi5", "geonhee", "hello"))
        posts.add(Post("hi6", "geonhee", "hello"))
        posts.add(Post("hi7", "geonhee", "hello"))
        posts.add(Post("hi8", "geonhee", "hello"))

        binding.postWritingButton.setOnClickListener {
            val intent = Intent(this, PostWritingActivity::class.java)
            intent.putExtra("posts", posts)
            startActivity(intent)
        }

        initViews()
    }
    private fun initViews() {
        if ((intent.getSerializableExtra("posts")) != null) {
            posts = intent.getSerializableExtra("posts") as ArrayList<Post>
        }
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