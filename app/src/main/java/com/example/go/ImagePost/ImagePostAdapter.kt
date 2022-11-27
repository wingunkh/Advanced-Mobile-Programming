package com.example.go.ImagePost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.go.PostViewModel
import com.example.go.databinding.ItemImagePostBinding
import com.example.go.Model.ImagePost
import com.example.go.R
import com.example.go.Utils.FBAuth
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
                if(viewModel.getUser(FBAuth.getUid()).imgUri == "") {
                    itemImagePostUserProfile.setImageResource(R.drawable.user)
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Glide.with(itemView.context)
                            .load(viewModel.getUser(FBAuth.getUid()).imgUri.toUri())
                            .into(itemImagePostUserProfile)
                    }
                }
                if(imagePost.imgUri=="") {
                    itemImagePostImage.setImageResource(R.drawable.muhan)
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Glide.with(itemView.context)
                            .load(imagePost.imgUri.toUri())
                            .into(itemImagePostImage)
                    }
                }
                itemImagePostUsername.text = imagePost.username
                itemImagePostContent.text = imagePost.content
//                itemImagePostFavoriteBtn.setOnClickListener() {
//                    FBRef.userRef.child(FBAuth.getUid()).child("favorite").child(imagePost.pid).setValue(imagePost)
//                }
            }
        }
    }

    override fun onBindViewHolder(holder: ImagePostViewHolder, position: Int) {
        holder.bind(viewModel.imagePostLiveData.value!![position])
    }

    override fun getItemCount() : Int = viewModel.imagePostLiveData.value?.size ?: 0


}