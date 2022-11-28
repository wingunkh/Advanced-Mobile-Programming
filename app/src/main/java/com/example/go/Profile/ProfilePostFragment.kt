package com.example.go.Profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.Post.ProfilePostAdapter
import com.example.go.PostViewModel
import com.example.go.databinding.FragmentProfilePostBinding

class ProfilePostFragment(private var uid: String) : Fragment() {
    private lateinit var binding: FragmentProfilePostBinding
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePostBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.profilePostList.apply {
            viewModel.getProfileTextPostList(uid)
            viewModel.getProfileTextPostList(uid)

            Log.d("uid from ProfilePostFragment ", uid)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ProfilePostAdapter(viewModel)
        }
    }

    fun newInstant() : ProfilePostFragment {
        val args = Bundle()
        val frag = ProfilePostFragment(uid)
        frag.arguments = args
        return frag
    }
}