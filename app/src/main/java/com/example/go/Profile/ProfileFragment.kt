//고급 모바일 프로그래밍 팀 프로젝트

package com.example.go.Profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.go.ImagePost.ImagePostListFragment
import com.example.go.MainActivity
import com.example.go.PostViewModel
import com.example.go.R
import com.example.go.Utils.FBAuth
import com.example.go.databinding.FragmentProfileBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // 프로필 유저 정보 가져오기
        if(viewModel.getUser(FBAuth.getUid()).imgUri=="") {
            binding.profileUserImage.setImageResource(R.drawable.user)
        } else {
            binding.profileUserImage.setImageURI(viewModel.getUser(FBAuth.getUid()).imgUri.toUri())
        }
        binding.profileUserName.text = FBAuth.getDisplayName()

        // 프로필 내 프래그먼트 어답터 연결
        val pageAdapter = ProfileAdapter(childFragmentManager)
        val pager = binding.profileViewPager
        pager.adapter = pageAdapter
        val tab = binding.profileTab
        tab.setupWithViewPager(pager)

        binding.profileBtn.setOnClickListener {
            (activity as MainActivity).changeFragmentWithBackStack(
                ProfileEditFragment.newInstance())
        }

        // 프로필 편집 또는 팔로우 기능
//        when(binding.profileBtn.text) {
//            "Edit Profile" -> (activity as MainActivity).changeFragmentWithBackStack(
//                ProfileEditFragment.newInstance())
//            "Follow" -> TODO()
//            else -> (activity as MainActivity).changeFragmentWithBackStack(
//                ProfileEditFragment.newInstance())
//        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}