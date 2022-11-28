package com.example.go.Profile

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.go.Model.ImagePost
import com.example.go.PostViewModel
import com.example.go.R
import com.example.go.Utils.FBRef
import com.example.go.databinding.ItemProfileImagePostBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileImagePostAdapter(private val viewModel: PostViewModel, private val uid: String) : RecyclerView.Adapter<ProfileImagePostAdapter.ProfileImagePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileImagePostViewHolder {
        val binding = ItemProfileImagePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileImagePostViewHolder(binding)
    }

    inner class ProfileImagePostViewHolder(val binding: ItemProfileImagePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePost: ImagePost) {

            binding.apply {
                if(uid!=null) {
                    FBRef.imagePostRef.child(uid).addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if(uid == dataSnapshot.child("uid").value.toString()){
                                Log.d("uid is ", uid)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) { } })
                }

                if(imagePost.imgUri=="") {
                    itemProfileImagePostImage.setImageResource(R.drawable.user)
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Glide.with(itemView.context)
                            .load(imagePost.imgUri.toUri())
                            .into(itemProfileImagePostImage)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ProfileImagePostViewHolder, position: Int) {
        holder.bind(viewModel.imagePostLiveData.value!![position])
    }

    override fun getItemCount() : Int = viewModel.imagePostLiveData.value?.size ?: 0


}