//고급 모바일 프로그래밍 팀 프로젝트

package com.example.go.Profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.ImagePost.ImagePostListFragment
import com.example.go.MainActivity
import com.example.go.Post.PostListFragment
import com.example.go.PostViewModel
import com.example.go.Search.SearchFragment
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.example.go.databinding.FragmentProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.ramotion.circlemenu.CircleMenuView


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val pageAdapter = ProfileAdapter(childFragmentManager)
        val pager = binding.profileViewPager
        pager.adapter = pageAdapter
        val tab = binding.profileTab
        tab.setupWithViewPager(pager)


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

                when (index) {
                    0 -> (activity as MainActivity).changeFragmentWithBackStack(ImagePostListFragment.newInstance())
                    1 -> (activity as MainActivity).changeFragmentWithBackStack(PostListFragment.newInstance())
                    2 -> (activity as MainActivity).changeFragmentWithBackStack(SearchFragment.newInstance())
                    3 -> (activity as MainActivity).changeFragmentWithBackStack(PostListFragment.newInstance())
                    // 이게 굳이?
                    4 -> (activity as MainActivity).changeFragmentWithBackStack(newInstance())
                }
            }

            override fun onButtonLongClick(
                view: CircleMenuView,
                buttonIndex: Int,
            ): Boolean {
                Log.d("D", "onButtonLongClick|index: $buttonIndex")
                return true
            }

            override fun onButtonLongClickAnimationStart(
                view: CircleMenuView,
                buttonIndex: Int,
            ) {
                Log.d("D", "onButtonLongClickAnimationStart|index: $buttonIndex")
            }

            override fun onButtonLongClickAnimationEnd(
                view: CircleMenuView,
                buttonIndex: Int,
            ) {
                Log.d("D", "onButtonLongClickAnimationEnd|index: $buttonIndex")
                when (buttonIndex) {
                    0 -> (activity as MainActivity).changeFragmentWithBackStack(ImagePostListFragment.newInstance())
                    1 -> (activity as MainActivity).changeFragmentWithBackStack(PostListFragment.newInstance())
                    2 -> (activity as MainActivity).changeFragmentWithBackStack(SearchFragment.newInstance())
                    3 -> (activity as MainActivity).changeFragmentWithBackStack(PostListFragment.newInstance())
                    // 이게 굳이?
                    4 -> (activity as MainActivity).changeFragmentWithBackStack(newInstance())
                }
            }
        }

//        FBRef.profileRef.child(FBAuth.getDisplayName()).setValue(FBAuth.getDisplayName())
//        FBRef.profileRef.child(FBAuth.getDisplayName()).child("name").setValue(FBAuth.getDisplayName())
//
//
//        FBRef.profileRef.child(FBAuth.getDisplayName()).child("name").addListenerForSingleValueEvent(object :
//            ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val name = dataSnapshot?.value
//                binding.profileUserName.text= name as CharSequence?
//            }
//            override fun onCancelled(error: DatabaseError) { } })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}