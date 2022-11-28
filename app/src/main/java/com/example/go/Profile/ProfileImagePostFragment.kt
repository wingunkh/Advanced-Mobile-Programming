package com.example.go.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.go.Model.ImagePost
import com.example.go.PostViewModel
import com.example.go.databinding.FragmentProfileImagePostBinding

class ProfileImagePostFragment(private val uid: String) : Fragment() {
    private lateinit var binding: FragmentProfileImagePostBinding
    private val viewModel by activityViewModels<PostViewModel>()
    private  var myList = arrayListOf<ImagePost>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileImagePostBinding.inflate(inflater, container, false)

        viewModel.imagePostLiveData.observe(viewLifecycleOwner, Observer {
            myList.clear()
            myList.addAll(it)
            initView()
        })

        return binding.root
    }

    private fun initView() {
        binding.profileImagePostList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = ProfileImagePostAdapter(myList)
        }
    }

    fun newInstant() : ProfileImagePostFragment {
        val args = Bundle()
        val frag = ProfileImagePostFragment(uid)
        frag.arguments = args
        return frag
    }
}