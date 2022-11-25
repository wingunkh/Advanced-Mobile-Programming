package com.example.go.ImagePost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.MainActivity
import com.example.go.Model.ImagePost
import com.example.go.PostViewModel
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.example.go.databinding.FragmentImagePostWriteBinding

private const val POSITION = "position"

class ImagePostWriteFragment : Fragment() {

    private lateinit var binding : FragmentImagePostWriteBinding
    private val viewModel by activityViewModels<PostViewModel>()

    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentImagePostWriteBinding.inflate(inflater, container, false)

        arguments?.let {
            position = it.getInt(POSITION)
        }

        binding.imagePostWriteBtn.setOnClickListener {
            val newPostKey = FBRef.imagePostRef.child(FBAuth.getUid()).push().key!!
            val postContent = binding.imagePostWriteContent.text.toString()
            val postUid = FBAuth.getUid()
            val postUser = FBAuth.getDisplayName()
            val postDate = FBAuth.getTime()
            viewModel.createImagePostItem(newPostKey, ImagePost(newPostKey, postUid, 0, postUser, postContent, postDate))
            (activity as MainActivity).removeFragment(this@ImagePostWriteFragment)

            return@setOnClickListener
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImagePostWriteFragment()
    }
}