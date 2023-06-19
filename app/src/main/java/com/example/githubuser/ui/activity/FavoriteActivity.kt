package com.example.githubuser.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.ui.adapter.FavoriteAdapter
import com.example.githubuser.ui.factory.ViewModelFactory
import com.example.githubuser.ui.models.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite List"

        showLoading(true)
        favoriteViewModel.getFavorite().observe(this) { gitList ->
            if (gitList != null) {
                adapter.setListFavorite(gitList)
            }
        }
        adapter = FavoriteAdapter()
        binding.rvFav.layoutManager = LinearLayoutManager(this)
        binding.rvFav.setHasFixedSize(true)
        binding.rvFav.adapter = adapter
        showLoading(false)

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}