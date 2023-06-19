package com.example.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class GitHubResponse(

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)

data class ItemsItem(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,
)
