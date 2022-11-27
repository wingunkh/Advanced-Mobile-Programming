package com.example.go.Search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go.PostViewModel
import com.example.go.Model.UserModel
import com.example.go.R
import com.example.go.databinding.ItemSearchProfileBinding

class SearchAdapter(private val viewModel: PostViewModel, private val query:String) : RecyclerView.Adapter<SearchAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemSearchProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    inner class PostViewHolder(val binding: ItemSearchProfileBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserModel) {
            binding.apply {
                if(user.uid==query) {
                    //itemSearchUserProfile.setImageResource(R.drawable.ic_baseline_search_24)
                    itemSearchUsername.text = user.displayName
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(viewModel.userLiveData.value!![position])
    }

    override fun getItemCount() : Int = viewModel.textPostLiveData.value?.size ?: 0


}