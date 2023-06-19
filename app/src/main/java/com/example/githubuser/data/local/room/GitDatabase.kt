package com.example.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuser.data.local.entity.GitEntity


@Database(entities = [GitEntity::class], version = 1, exportSchema = false)
abstract class GitDatabase : RoomDatabase() {
    abstract fun gitDao(): GitDao

    companion object{
        @Volatile
        private var INSTANCE: GitDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GitDatabase{
            if (INSTANCE == null){
                synchronized(GitDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        GitDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as GitDatabase
        }
    }
}