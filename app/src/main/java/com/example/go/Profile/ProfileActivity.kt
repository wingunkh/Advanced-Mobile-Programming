//고급 모바일 프로그래밍 팀 프로젝트

package com.example.go.Profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.example.go.ImagePost.ImageListActivity
import com.example.go.Post.PostListActivity
import com.example.go.databinding.ActivityProfileBinding
import com.ramotion.circlemenu.CircleMenuView


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menu = binding.circleMenu
        menu.eventListener = object : CircleMenuView.EventListener() {
            override fun onMenuOpenAnimationStart(view: CircleMenuView) {
                Log.d("D", "onMenuOpenAnimationStart")
            }

            override fun onMenuOpenAnimationEnd(view: CircleMenuView) {
                Log.d("D", "onMenuOpenAnimationEnd")
            }

            override fun onMenuCloseAnimationStart(view: CircleMenuView) {
                Log.d("D", "onMenuCloseAnimationStart")
            }

            override fun onMenuCloseAnimationEnd(view: CircleMenuView) {
                Log.d("D", "onMenuCloseAnimationEnd")
            }

            override fun onButtonClickAnimationStart(view: CircleMenuView, index: Int) {
                Log.d("D", "onButtonClickAnimationStart|index: $index")
            }

            override fun onButtonClickAnimationEnd(view: CircleMenuView, index: Int) {
                Log.d("D", "onButtonClickAnimationEnd|index: $index")

                when(index) {
                    0 -> startActivity(Intent(baseContext, ImageListActivity::class.java))
                    1 -> startActivity(Intent(baseContext, PostListActivity::class.java))
                    2 -> startActivity(Intent(baseContext, ImageListActivity::class.java))
                    3 -> startActivity(Intent(baseContext, PostListActivity::class.java))
                    4 -> startActivity(Intent(baseContext, ProfileActivity::class.java))
                }
            }

            override fun onButtonLongClick(
                view: CircleMenuView,
                buttonIndex: Int
            ): Boolean {
                Log.d("D", "onButtonLongClick|index: $buttonIndex")
                return true
            }

            override fun onButtonLongClickAnimationStart(
                view: CircleMenuView,
                buttonIndex: Int
            ) {
                Log.d("D", "onButtonLongClickAnimationStart|index: $buttonIndex")
            }

            override fun onButtonLongClickAnimationEnd(
                view: CircleMenuView,
                buttonIndex: Int
            ) {
                Log.d("D", "onButtonLongClickAnimationEnd|index: $buttonIndex")
                when(buttonIndex) {
                    0 -> startActivity(Intent(baseContext, ImageListActivity::class.java))
                    1 -> startActivity(Intent(baseContext, PostListActivity::class.java))
                    2 -> startActivity(Intent(baseContext, ImageListActivity::class.java))
                    3 -> startActivity(Intent(baseContext, PostListActivity::class.java))
                    4 -> startActivity(Intent(baseContext, ProfileActivity::class.java))
                }
            }
        }
    }
}