package com.example.go.ImagePost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go.databinding.ItemImagePostBinding

class ImagePostAdapter(private val posts: List<ImagePost>) : RecyclerView.Adapter<ImagePostAdapter.ImagePostViewHolder>() {
    interface ItemClick {
        fun onClick(view: View, position:Int)
    }
    var itemClick: ItemClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePostViewHolder {
        val binding = ItemImagePostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ImagePostViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ImagePostViewHolder, position: Int) {
        holder.bind(posts[position])
        holder.binding.postImageView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
    }

    override fun getItemCount() : Int = posts.size

    class ImagePostViewHolder(val binding: ItemImagePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePost: ImagePost) {
            binding.postUserText.text = imagePost.user
            binding.postImageView.setImageResource(imagePost.imgSrc)
        }
    }
}