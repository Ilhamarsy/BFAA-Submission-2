package com.dicoding.githubuser.api

import com.dicoding.githubuser.response.DetailUserResponse
import com.dicoding.githubuser.response.SearchResponse
import com.dicoding.githubuser.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUser(): Call<ArrayList<UserResponse>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearch(
        @Query("q") query: String
    ) : Call<SearchResponse>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserResponse>>
}