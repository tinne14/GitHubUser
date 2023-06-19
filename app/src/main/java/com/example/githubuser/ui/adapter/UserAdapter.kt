package com.example.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.ui.activity.DetailActivity.Companion.EXTRA_DETAIL
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.ui.activity.DetailActivity

class UserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val itemBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserAdapter.ViewHolder(itemBinding)
    }
    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvItemName.text = listUser[position].login
        Glide.with(holder.itemView)
            .load(listUser[position].avatarUrl)
            .circleCrop()
            .into(holder.binding.imgUserPhoto)
        holder.itemView.setOnClickListener {
            val moveDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            moveDetail.putExtra(EXTRA_DETAIL, listUser[holder.adapterPosition].login)
            holder.itemView.context.startActivity(moveDetail)
        }
    }
}



