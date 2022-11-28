package com.example.go.Post

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go.PostViewModel
import com.example.go.databinding.ItemPostBinding
import com.example.go.Model.TextPost

class ProfilePostAdapter(private val viewModel: PostViewModel, private val uid: String) : RecyclerView.Adapter<ProfilePostAdapter.ProfilePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfilePostViewHolder(binding)
    }

    inner class ProfilePostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: TextPost) {
            binding.apply {
                itemPostTitleText.text = post.title
                itemPostUsername.text = post.username
                itemPostDate.text = post.date
                Log.d("post222 : ", post.title)
            }
        }
    }

    override fun onBindViewHolder(holder: ProfilePostViewHolder, position: Int) {
        holder.bind(viewModel.profileTextPostLiveData.value!![position])
    }

    override fun getItemCount() : Int = viewModel.profileTextPostLiveData.value?.size ?: 0

}