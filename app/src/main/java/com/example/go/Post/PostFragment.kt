package com.example.go.Post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.PostViewModel
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.example.go.databinding.FragmentPostBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

private const val POSITION = "position"

class PostFragment : Fragment(){

    private lateinit var binding: FragmentPostBinding
    private val viewModel by activityViewModels<PostViewModel>()

    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentPostBinding.inflate(inflater, container, false)

        arguments?.let {
            position = it.getInt(POSITION)
        }

        binding.apply {
            writePostTitleText.text = viewModel.textPostLiveData.value!![position].title
            binding.writePostUsername.text = viewModel.textPostLiveData.value!![position].user
            binding.writePostContentText.text = viewModel.textPostLiveData.value!![position].content
        }

        binding.writePostUsername.setOnClickListener(){
            FBRef.followingRef.child(FBAuth.getDisplayName()).setValue(FBAuth.getDisplayName())
            FBRef.followingRef.child(FBAuth.getDisplayName()).child(binding.writePostUsername.text.toString()).setValue(binding.writePostUsername.text.toString())
        }

        FBRef.followingRef.child(FBAuth.getDisplayName()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot?.value as Map<*, *>?
                var followingCheck = map?.get("${binding.writePostUsername.text}")?.toString()
                if(followingCheck!=null){
                    binding.ggami.text = "당신은 이 사람과 친구군요!"
                    binding.ggami.visibility=View.VISIBLE
                }
            }
            override fun onCancelled(error: DatabaseError) { } })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int) = PostFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION, position)
            }
        }
    }
}