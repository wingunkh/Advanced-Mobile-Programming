//고급 모바일 프로그래밍 팀 프로젝트

package com.example.go.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.PostViewModel
import com.example.go.R
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.example.go.databinding.FragmentProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

private const val UID = "uid"

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by activityViewModels<PostViewModel>()

    private var uid: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        arguments?.let {
            uid = it.getString(UID).toString()
        }

        // 프로필 유저 정보 가져오기
            FBRef.userRef.child(uid).child("displayName").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    binding.profileUserName.text = dataSnapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
//                CoroutineScope(Dispatchers.Main).launch {
//                    Glide.with(requireContext())
//                        .load(viewModel.getUser(uid))
//                        .into(profileUserImage)
//                }

        // 프로필 내 프래그먼트 어댑터 연결
        val pageAdapter = ProfileAdapter(childFragmentManager, uid)
        val pager = binding.profileViewPager
        pager.adapter = pageAdapter
        val tab = binding.profileTab
        tab.setupWithViewPager(pager)

        FBRef.userRef.child(FBAuth.getUid()).child("following").child(uid).addValueEventListener(object :
        ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (uid == snapshot.value.toString()) { //이미 팔로우 중이라면
                    binding.profileViewPager.visibility = View.VISIBLE //게시글 보임
                    binding.profileBtn.text = "following" //프로필의 버튼이 "following" 라고 뜸
                }
                else { //아직 팔로우 하지 않았다면
                    binding.profileViewPager.visibility = View.GONE //게시글 안 보임
                    binding.profileBtn.text = "follow" //프로필의 버튼이 "follow" 라고 뜸
                    if(uid == FBAuth.getUid()) { //아직 팔로우 하지 않았으나 본인의 프로필이라면
                        binding.profileViewPager.visibility = View.VISIBLE //게시글 보임
                        binding.profileBtn.text = "my profile" //프로필의 버튼이 "my profile 라고 뜸
                    }

                }
            } override fun onCancelled(error: DatabaseError) {}
        })

        binding.profileBtn.setOnClickListener {
            if(FBAuth.getUid()!=uid) //나 자신이 아닐 때
                FBRef.userRef.child(FBAuth.getUid()).child("following").child(uid).setValue(uid) //팔로우
            if(binding.profileBtn.text=="following") //프로필의 버튼이 "following" 일 때
                FBRef.userRef.child(FBAuth.getUid()).child("following").child(uid).removeValue() //언팔로우
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(uid: String) = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString(UID, uid)
            }
        }
    }
}