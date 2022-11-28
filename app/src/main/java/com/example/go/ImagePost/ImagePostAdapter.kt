package com.example.go.ImagePost

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.go.databinding.ItemImagePostBinding
import com.example.go.Model.ImagePost
import com.example.go.R

class ImagePostAdapter(private val myList: List<ImagePost>) : RecyclerView.Adapter<ImagePostAdapter.ImagePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePostViewHolder {
        val binding = ItemImagePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagePostViewHolder(binding)
    }

    inner class ImagePostViewHolder(val binding: ItemImagePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePost: ImagePost) {

            binding.apply {
                if(imagePost.imgUri=="") {
                    itemImagePostImage.setImageResource(R.drawable.muhan)
                } else {
                        Glide.with(itemView.context)
                            .load(imagePost.imgUri)
                            .into(itemImagePostImage)
                }
                itemImagePostUsername.text = imagePost.username
                itemImagePostContent.text = imagePost.content
            }
        }
    }

    override fun onBindViewHolder(holder: ImagePostViewHolder, position: Int) {
        holder.bind(myList[position])
    }

    override fun getItemCount() : Int = myList.size


}