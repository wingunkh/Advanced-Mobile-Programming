package com.example.go.ImagePost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go.PostViewModel
import com.example.go.databinding.ItemImagePostBinding
import com.example.go.Model.ImagePost

class ImagePostAdapter(private val viewModel: PostViewModel) : RecyclerView.Adapter<ImagePostAdapter.ImagePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePostViewHolder {
        val binding = ItemImagePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagePostViewHolder(binding)
    }

    inner class ImagePostViewHolder(val binding: ItemImagePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePost: ImagePost) {
            binding.apply {
                itemImagePostUsername.text = imagePost.username
                itemImagePostContent.text = imagePost.content
                itemImagePostImage.setImageResource(imagePost.imgSrc)
            }
        }
    }

    override fun onBindViewHolder(holder: ImagePostViewHolder, position: Int) {
        holder.bind(viewModel.imagePostLiveData.value!![position])
    }

    override fun getItemCount() : Int = viewModel.imagePostLiveData.value?.size ?: 0


}