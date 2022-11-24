package com.example.go.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.go.ImagePost.ImagePostAdapter
import com.example.go.PostViewModel
import com.example.go.databinding.FragmentProfileImagePostBinding

class ProfileImagePostFragment : Fragment() {
    private lateinit var binding: FragmentProfileImagePostBinding
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileImagePostBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {

        binding.profileImagePostList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = ProfileImagePostAdapter(viewModel)
        }
    }

    fun newInstant() : ProfileImagePostFragment {
        val args = Bundle()
        val frag = ProfileImagePostFragment()
        frag.arguments = args
        return frag
    }
}