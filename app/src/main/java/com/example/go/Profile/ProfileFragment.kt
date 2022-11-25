//고급 모바일 프로그래밍 팀 프로젝트

package com.example.go.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.PostViewModel
import com.example.go.Utils.FBAuth
import com.example.go.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val pageAdapter = ProfileAdapter(childFragmentManager)
        val pager = binding.profileViewPager
        pager.adapter = pageAdapter
        val tab = binding.profileTab
        tab.setupWithViewPager(pager)

        binding.profileUserName.text = FBAuth.getDisplayName()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}