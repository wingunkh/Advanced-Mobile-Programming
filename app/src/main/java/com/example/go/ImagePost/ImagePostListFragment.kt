package com.example.go.ImagePost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.MainActivity
import com.example.go.PostViewModel
import com.example.go.databinding.FragmentImagePostListBinding

class ImagePostListFragment : Fragment() {

    private lateinit var binding: FragmentImagePostListBinding
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentImagePostListBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.imagePostBtn.setOnClickListener {
            (activity as MainActivity).changeFragmentWithBackStack(ImagePostWriteFragment.newInstance())
        }

        binding.imagePostList.apply {
            setHasFixedSize(true)
            viewModel.initImagePostList()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ImagePostAdapter(viewModel)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImagePostListFragment()
    }
}