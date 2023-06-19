package com.example.githubuser.data

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.data.local.entity.GitEntity

class GitDiffCallback(private val mOldFavList: List<GitEntity>, private val mNewFavList: List<GitEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavList.size
    }
    override fun getNewListSize(): Int {
        return mNewFavList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavList[oldItemPosition].username == mNewFavList[newItemPosition].username
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavList[oldItemPosition]
        val newEmployee = mNewFavList[newItemPosition]
        return oldEmployee.username == newEmployee.username && oldEmployee.avatarUrl == newEmployee.avatarUrl
    }
}