package com.example.go.Post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.PostViewModel
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.example.go.databinding.FragmentPostBinding

private const val POSITION = "position"

class PostFragment : Fragment(){

    private lateinit var binding: FragmentPostBinding
    private val viewModel by activityViewModels<PostViewModel>()

    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentPostBinding.inflate(inflater, container, false)

        arguments?.let {
            position = it.getInt(POSITION)
        }

        binding.apply {
            writePostTitleText.text = viewModel.textPostLiveData.value!![position].title
            binding.writePostUsername.text = viewModel.textPostLiveData.value!![position].username
            binding.writePostContentText.text = viewModel.textPostLiveData.value!![position].content
        }

        binding.writePostUsername.setOnClickListener() {
            FBRef.followingRef.child(FBAuth.getDisplayName()).setValue(FBAuth.getDisplayName())
            FBRef.followingRef.child(FBAuth.getDisplayName())
                .child(binding.writePostUsername.text.toString())
                .setValue(binding.writePostUsername.text.toString())
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int) = PostFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION, position)
            }
        }
    }
}