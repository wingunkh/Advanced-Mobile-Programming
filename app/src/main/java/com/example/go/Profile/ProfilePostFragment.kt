package com.example.go.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.MainActivity
import com.example.go.Post.PostAdapter
import com.example.go.Post.PostFragment
import com.example.go.PostViewModel
import com.example.go.databinding.FragmentProfilePostBinding

class ProfilePostFragment : Fragment() {
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
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = PostAdapter(viewModel) {
                (activity as MainActivity).changeFragmentWithBackStack(PostFragment.newInstance(it))
            }
        }
    }

    fun newInstant() : ProfilePostFragment {
        val args = Bundle()
        val frag = ProfilePostFragment()
        frag.arguments = args
        return frag
    }
}