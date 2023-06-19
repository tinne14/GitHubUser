package com.example.githubuser.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.local.entity.GitEntity
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.ui.adapter.SectionPagerAdapter
import com.example.githubuser.ui.factory.ViewModelFactory
import com.example.githubuser.ui.models.DetailViewModel
import com.example.githubuser.ui.models.FavoriteViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    private lateinit var binding: ActivityDetailBinding

    private lateinit var url: String

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private val detailViewModel by viewModels<DetailViewModel>()

    private var favUser: GitEntity? = null
    private var cekFav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var username = intent.getStringExtra(EXTRA_DETAIL)
        val sectionsPagerAdapter = SectionPagerAdapter(this)
        sectionsPagerAdapter.username = username.toString()
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        supportActionBar?.title = username


        detailViewModel.isLoading.observe(this, Observer{
            showLoading(it)
        })

        detailViewModel.listDetailUser.observe(this, Observer{detail ->
            binding.apply {
                url = detail.url.toString()
                tvName.text = detail.name
                tvLogin.text = detail.login
                tvFollowing.text = "${detail.following} Following"
                tvFollowers.text = "${detail.followers} Followers"
                tvRepo.text = "${detail.publicRepos} Repository"
                if (detail.location != null){
                    imageView2.visibility = View.VISIBLE
                    tvLocation.text = detail.location
                } else {
                    imageView2.visibility = View.INVISIBLE
                    tvLocation.text = "-"
                }


                Glide.with(this@DetailActivity)
                    .load(detail.avatarUrl)
                    .circleCrop()
                    .into(imgDetailPhoto)

                favUser = GitEntity(detail.login.toString(), detail.avatarUrl)

                favoriteViewModel.getFavorite().observe(this@DetailActivity){
                    if (it != null){
                        for (data in it){
                            if(detail.login == data.username){
                                cekFav = true
                                binding.fav.setImageResource(R.drawable.ic_favorite_24)
                                binding.fav.imageTintList =
                                    ColorStateList.valueOf(Color.rgb(186, 0, 1))
                                Log.d("Favorite", "Berhasil")
                            }
                        }
                    }
                }

                binding.fav.setOnClickListener{
                    if (!cekFav) {
                        cekFav = true
                        binding.fav.setImageResource(R.drawable.ic_favorite_24)
                        binding.fav.imageTintList = ColorStateList.valueOf(Color.rgb(186, 0, 1))
                        favoriteViewModel.insert(favUser as GitEntity)
                    } else {
                        cekFav = false
                        binding.fav.setImageResource(R.drawable.ic_favorite_border_24)
                        binding.fav.imageTintList = ColorStateList.valueOf(Color.rgb(255, 255, 255))
                        favoriteViewModel.delete(detail.login.toString())

                    }
                }
            }
        })
        detailViewModel.detailUser(username.toString())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("${url}"))
                startActivity(browserIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}