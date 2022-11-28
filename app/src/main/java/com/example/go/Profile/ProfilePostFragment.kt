package com.example.go.Profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.Model.TextPost
import com.example.go.Post.ProfilePostAdapter
import com.example.go.PostViewModel
import com.example.go.databinding.FragmentProfilePostBinding

class ProfilePostFragment(private var uid: String) : Fragment() {
    private lateinit var binding: FragmentProfilePostBinding
    private val viewModel by activityViewModels<PostViewModel>()
    private  var myList = arrayListOf<TextPost>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePostBinding.inflate(inflater, container, false)

        viewModel.profileTextPostLiveData.observe(viewLifecycleOwner, Observer {
            myList.clear()
            myList.addAll(it)
            initView()
        })

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView() {
        binding.profilePostList.apply {
            viewModel.getProfileTextPostList(uid)

            Log.d("uid from ProfilePostFragment ", uid)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ProfilePostAdapter(myList)
        }
    }

    fun newInstant() : ProfilePostFragment {
        val args = Bundle()
        val frag = ProfilePostFragment(uid)
        frag.arguments = args
        return frag
    }
}