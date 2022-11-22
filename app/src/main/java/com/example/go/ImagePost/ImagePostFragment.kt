package com.example.go.ImagePost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.PostViewModel
import com.example.go.databinding.FragmentImagePostBinding

private const val POSITION = "position"

class ImagePostFragment : Fragment() {

    private lateinit var binding: FragmentImagePostBinding
    private val viewModel by activityViewModels<PostViewModel>()

    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentImagePostBinding.inflate(inflater, container, false)

        arguments?.let {
            position = it.getInt(POSITION)
        }

        initView()

        return binding.root
    }

    private fun initView() {
        binding.apply {
            imagePostUserText.text = viewModel.imagePostLiveData.value!![position].user
            imagePostContentText.text = viewModel.imagePostLiveData.value!![position].content
            imagePostTitleText.text = viewModel.imagePostLiveData.value!![position].title
            postImageView.setImageResource(viewModel.imagePostLiveData.value!![position].imgSrc)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int) = ImagePostFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION, position)
            }
        }
    }
}