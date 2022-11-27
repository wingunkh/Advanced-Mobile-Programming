//고급 모바일 프로그래밍 팀 프로젝트

package com.example.go.Profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.go.MainActivity
import com.example.go.PostViewModel
import com.example.go.R
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.example.go.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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
            CoroutineScope(Dispatchers.Main).launch {
                Glide.with(requireContext())
                    .load(viewModel.getUser(FBAuth.getUid()).imgUri)
                    .into(binding.profileUserImage)
            }
        }
        FBRef.userRef.child(FBAuth.getUid()).child("displayName").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                binding.profileUserName.text = dataSnapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) { } })

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

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}