package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.data.local.entity.GitEntity

@Dao
interface GitDao {
    @Query("SELECT * FROM favorite")
    fun getFavorite(): LiveData<List<GitEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(fav: GitEntity)

    @Update
    fun updateFavorite(fav: GitEntity)

    @Query("DELETE FROM favorite WHERE username = :username")
    fun delete(username: String)

}