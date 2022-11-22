//고급 모바일 프로그래밍 팀 프로젝트

package com.example.go.Post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.MainActivity
import com.example.go.PostViewModel
import com.example.go.databinding.FragmentPostListBinding

class PostListFragment : Fragment() {
    private lateinit var binding: FragmentPostListBinding
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPostListBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.postWritingButton.setOnClickListener {
            (activity as MainActivity).changeFragmentWithBackStack(PostWriteFragment.newInstance())
        }

        binding.postList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = PostAdapter(viewModel) {
                (activity as MainActivity).changeFragmentWithBackStack(PostFragment.newInstance(it))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostListFragment()
    }
}