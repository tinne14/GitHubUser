package com.example.githubuser.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.GitDiffCallback
import com.example.githubuser.data.local.entity.GitEntity
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.ui.activity.DetailActivity

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.ViewHolder>()  {

    private val listFavorite = ArrayList<GitEntity>()

    fun setListFavorite(listFavs: List<GitEntity>) {
        val diffCallback = GitDiffCallback(this.listFavorite, listFavs)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavs)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = listFavorite.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fav = listFavorite[position]
        holder.bind(fav)
        holder.itemView.setOnClickListener {
            val moveDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            moveDetail.putExtra(DetailActivity.EXTRA_DETAIL, listFavorite[holder.adapterPosition].username)
            holder.itemView.context.startActivity(moveDetail)
        }
    }

    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(fav: GitEntity) {
            binding.tvItemName.text = fav.username
            Glide.with(itemView.context)
                .load(fav.avatarUrl)
                .circleCrop()
                .into(binding.imgUserPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<GitEntity> =
            object : DiffUtil.ItemCallback<GitEntity>() {
                override fun areItemsTheSame(oldUser: GitEntity, newUser: GitEntity): Boolean {
                    return oldUser.username == newUser.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: GitEntity, newUser: GitEntity): Boolean {
                    return oldUser == newUser
                }
            }
    }
}