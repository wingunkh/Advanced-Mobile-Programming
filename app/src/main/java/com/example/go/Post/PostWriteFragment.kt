package com.example.go.Post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.MainActivity
import com.example.go.PostViewModel
import com.example.go.databinding.FragmentPostWriteBinding
import com.example.go.Model.TextPost
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

private const val POSITION = "position"

class PostWriteFragment : Fragment() {

    private lateinit var binding : FragmentPostWriteBinding
    private val viewModel by activityViewModels<PostViewModel>()

    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPostWriteBinding.inflate(inflater, container, false)
        FBRef.userRef.child(FBAuth.getUid()).child("displayName").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                binding.postWritingWriter.text = dataSnapshot.value.toString()
            } override fun onCancelled(error: DatabaseError) {}
        })

        arguments?.let {
            position = it.getInt(POSITION)
        }

        binding.postWritingPostButton.setOnClickListener {

            val newPostKey = FBRef.postRef.child(FBAuth.getUid()).push().key!!
            val postTitle = binding.postWritingTitle.text.toString()
            val postContent = binding.postWritingContent.text.toString()
            val postUid = FBAuth.getUid()
            val postUser =  binding.postWritingWriter.text.toString()
            val postDate = FBAuth.getTime()
            viewModel.createTextPostItem(newPostKey, TextPost(newPostKey, postUid, postTitle, postUser, postContent, postDate))
            (activity as MainActivity).removeFragment(this@PostWriteFragment)

            (activity as MainActivity).changeFragmentWithBackStack(PostListFragment.newInstance())
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostWriteFragment()
    }
}