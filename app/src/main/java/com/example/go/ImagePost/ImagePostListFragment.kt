package com.example.go.ImagePost

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.Favorite.FavoriteFragment
import com.example.go.MainActivity
import com.example.go.Post.PostListFragment
import com.example.go.PostViewModel
import com.example.go.Profile.ProfileFragment
import com.example.go.Search.SearchFragment
import com.example.go.databinding.FragmentImagePostListBinding
import com.ramotion.circlemenu.CircleMenuView

class ImagePostListFragment : Fragment() {

    private lateinit var binding: FragmentImagePostListBinding
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentImagePostListBinding.inflate(inflater, container, false)

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
                    0 -> (activity as MainActivity).changeFragmentWithBackStack(PostListFragment.newInstance())
                    1 -> (activity as MainActivity).changeFragmentWithBackStack(ImagePostWriteFragment.newInstance())
                    2 -> (activity as MainActivity).changeFragmentWithBackStack(SearchFragment.newInstance())
                    3 -> (activity as MainActivity).changeFragmentWithBackStack(FavoriteFragment.newInstance())
                    4 -> (activity as MainActivity).changeFragmentWithBackStack(ProfileFragment.newInstance())
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
                    0 -> (activity as MainActivity).changeFragmentWithBackStack(PostListFragment.newInstance())
                    1 -> (activity as MainActivity).changeFragmentWithBackStack(ImagePostWriteFragment.newInstance())
                    2 -> (activity as MainActivity).changeFragmentWithBackStack(SearchFragment.newInstance())
                    3 -> (activity as MainActivity).changeFragmentWithBackStack(FavoriteFragment.newInstance())
                    4 -> (activity as MainActivity).changeFragmentWithBackStack(ProfileFragment.newInstance())
                }
            }
        }

        initView()

        return binding.root
    }

    private fun initView() {

        binding.imagePostList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ImagePostAdapter(viewModel)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImagePostListFragment()
    }
}