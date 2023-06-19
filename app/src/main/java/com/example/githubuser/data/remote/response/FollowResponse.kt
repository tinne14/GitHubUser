package com.example.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class FollowResponse(
	@field:SerializedName("FollowResponse")
	val followResponse: List<FollowResponseItem?>? = null
)

data class FollowResponseItem(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,
)
