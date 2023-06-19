package com.example.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.remote.response.FollowResponseItem
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.ui.activity.DetailActivity

class FollowAdapter (private val listFollowUser: List<FollowResponseItem>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowAdapter.ViewHolder(itemBinding)
    }

    override fun getItemCount() = listFollowUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvItemName.text = listFollowUser[position].login
        Glide.with(holder.itemView)
            .load(listFollowUser[position].avatarUrl)
            .circleCrop()
            .into(holder.binding.imgUserPhoto)

        holder.itemView.setOnClickListener {
            val moveDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            moveDetail.putExtra(DetailActivity.EXTRA_DETAIL, listFollowUser[holder.adapterPosition].login)
            holder.itemView.context.startActivity(moveDetail)
        }
    }
}