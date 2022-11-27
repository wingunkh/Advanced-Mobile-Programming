package com.example.go.Follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.MainActivity
import com.example.go.PostViewModel
import com.example.go.Profile.ProfileFragment
import com.example.go.Search.FollowAdapter
import com.example.go.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFollowBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {

        binding.followListView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = FollowAdapter(viewModel,"") {
                (activity as MainActivity).changeFragmentWithBackStack(ProfileFragment.newInstance(it))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FollowFragment()
    }
}