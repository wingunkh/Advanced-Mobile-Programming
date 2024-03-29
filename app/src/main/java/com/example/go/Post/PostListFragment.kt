//고급 모바일 프로그래밍 팀 프로젝트

package com.example.go.Post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.MainActivity
import com.example.go.Model.ImagePost
import com.example.go.Model.TextPost
import com.example.go.PostViewModel
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.example.go.databinding.FragmentPostListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class PostListFragment : Fragment() {
    private lateinit var binding: FragmentPostListBinding
    private val viewModel by activityViewModels<PostViewModel>()
    private  var myList = arrayListOf<TextPost>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPostListBinding.inflate(inflater, container, false)

        viewModel.textPostLiveData.observe(viewLifecycleOwner, Observer {
            myList.clear()
            myList.addAll(it)
            initView()
        })
        myList.clear()
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
            adapter = PostAdapter(myList) {
                (activity as MainActivity).changeFragmentWithBackStack(PostFragment.newInstance(it))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostListFragment()
    }
}