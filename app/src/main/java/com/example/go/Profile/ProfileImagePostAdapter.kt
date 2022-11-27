package com.example.go.Profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.go.Model.ImagePost
import com.example.go.PostViewModel
import com.example.go.R
import com.example.go.databinding.ItemProfileImagePostBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileImagePostAdapter(private val viewModel: PostViewModel) : RecyclerView.Adapter<ProfileImagePostAdapter.ProfileImagePostViewHolder>() {

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