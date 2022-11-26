package com.example.go.ImagePost

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.go.PostViewModel
import com.example.go.databinding.ItemImagePostBinding
import com.example.go.Model.ImagePost
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagePostAdapter(private val viewModel: PostViewModel) : RecyclerView.Adapter<ImagePostAdapter.ImagePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePostViewHolder {
        val binding = ItemImagePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagePostViewHolder(binding)
    }

    inner class ImagePostViewHolder(val binding: ItemImagePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePost: ImagePost) {

            binding.apply {

                itemImagePostImage.setImageURI(imagePost.imgUri.toUri())

                CoroutineScope(Dispatchers.Main).launch {
                    Glide.with(itemView.context)
                        .load(imagePost.imgUri)
                        .into(itemImagePostImage)
                }
                itemImagePostUsername.text = imagePost.username
                itemImagePostContent.text = imagePost.content
                itemImagePostFavoriteBtn.setOnClickListener() {
                    FBRef.userRef.child(FBAuth.getUid()).child("favorite").child(imagePost.pid).setValue(imagePost)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ImagePostViewHolder, position: Int) {
        holder.bind(viewModel.imagePostLiveData.value!![position])
    }

    override fun getItemCount() : Int = viewModel.imagePostLiveData.value?.size ?: 0


}