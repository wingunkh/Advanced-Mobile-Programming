package com.example.go.Post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.MainActivity
import com.example.go.PostViewModel
import com.example.go.databinding.FragmentPostWriteBinding
import com.example.go.Model.TextPost
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBAuth.getTime
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val POSITION = "position"

class PostWriteFragment : Fragment() {

    private lateinit var binding : FragmentPostWriteBinding
    private val viewModel by activityViewModels<PostViewModel>()

    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPostWriteBinding.inflate(inflater, container, false)

        arguments?.let {
            position = it.getInt(POSITION)
        }

        binding.postWritingPostButton.setOnClickListener {
            val postTitle = binding.postWritingTitle.text.toString()
            val postContent = binding.postWritingContent.text.toString()
            val postUser = FBAuth.getDisplayName()
            val postDate = getTime()
            viewModel.createTextPostItem(TextPost(postTitle, postUser, postContent, postDate))
            (activity as MainActivity).removeFragment(this@PostWriteFragment)

            return@setOnClickListener
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostWriteFragment()
    }
}