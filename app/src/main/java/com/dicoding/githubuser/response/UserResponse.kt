package com.dicoding.githubuser.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

)

data class SearchResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: ArrayList<UserResponse>
)