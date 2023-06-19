package com.example.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.data.local.entity.GitEntity
import com.example.githubuser.data.local.room.GitDao
import com.example.githubuser.data.local.room.GitDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GitRepository(application: Application) {

    private val mGitDao: GitDao
    private  val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GitDatabase.getDatabase(application)
        mGitDao = db.gitDao()
    }

    fun getFavorite(): LiveData<List<GitEntity>> = mGitDao.getFavorite()

    fun delete(username: String){
        executorService.execute { mGitDao.delete(username) }
    }

    fun insert(fav: GitEntity) {
        executorService.execute { mGitDao.insertFavorite(fav) }
    }
}