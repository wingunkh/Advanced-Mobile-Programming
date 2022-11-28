package com.example.go.Profile

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.go.Model.ImagePost
import com.example.go.R
import com.example.go.databinding.ItemProfileImagePostBinding

class ProfileImagePostAdapter(private val myList: List<ImagePost>) : RecyclerView.Adapter<ProfileImagePostAdapter.ProfileImagePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileImagePostViewHolder {
        val binding = ItemProfileImagePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileImagePostViewHolder(binding)
    }

    inner class ProfileImagePostViewHolder(val binding: ItemProfileImagePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePost: ImagePost) {

            binding.apply {
                if(imagePost.imgUri=="") {
                    itemProfileImagePostImage.setImageResource(R.drawable.user)
                } else {
                        Glide.with(itemView.context)
                            .load(imagePost.imgUri)
                            .into(itemProfileImagePostImage)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ProfileImagePostViewHolder, position: Int) {
        holder.bind(myList[position])
    }

    override fun getItemCount() : Int = myList.size


}