package com.example.githubuser.ui.models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.GitRepository
import com.example.githubuser.data.local.entity.GitEntity

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mGitRepository: GitRepository = GitRepository(application)

    fun insert(fav: GitEntity) {
        mGitRepository.insert(fav)
    }

    fun delete(username: String){
        mGitRepository.delete(username)
    }

    fun getFavorite(): LiveData<List<GitEntity>> = mGitRepository.getFavorite()

}