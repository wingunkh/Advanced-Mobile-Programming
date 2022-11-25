package com.example.go.Profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go.PostViewModel
import com.example.go.Model.ImagePost
import com.example.go.databinding.ItemProfileImagePostBinding

class ProfileImagePostAdapter(private val viewModel: PostViewModel) : RecyclerView.Adapter<ProfileImagePostAdapter.ProfileImagePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileImagePostViewHolder {
        val binding = ItemProfileImagePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileImagePostViewHolder(binding)
    }

    inner class ProfileImagePostViewHolder(val binding: ItemProfileImagePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePost: ImagePost) {
            binding.apply {
                itemProfileImagePostImage.setImageResource(imagePost.imgSrc)
            }
        }
    }

    override fun onBindViewHolder(holder: ProfileImagePostViewHolder, position: Int) {
        holder.bind(viewModel.imagePostLiveData.value!![position])
    }

    override fun getItemCount() : Int = viewModel.imagePostLiveData.value?.size ?: 0


}