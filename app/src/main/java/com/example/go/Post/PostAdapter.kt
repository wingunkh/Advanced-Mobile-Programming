package com.example.go.Post

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go.PostViewModel
import com.example.go.databinding.ItemPostBinding
import com.example.go.Model.TextPost

class PostAdapter(private val viewModel: PostViewModel, private val itemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    inner class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: TextPost) {
            binding.apply {
                itemPostTitleText.text = post.title
                itemPostUsername.text = post.username
                itemPostDate.text = post.date

                itemPostView.setOnClickListener {
                    itemClicked(adapterPosition)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        Log.d(ContentValues.TAG,"from PostAdapter : " + viewModel.textPostLiveData.value!![position])
        holder.bind(viewModel.textPostLiveData.value!![position])
    }

    override fun getItemCount() : Int = viewModel.textPostLiveData.value?.size ?: 0


}