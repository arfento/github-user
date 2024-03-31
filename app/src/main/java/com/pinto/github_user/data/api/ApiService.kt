package com.pinto.github_user.data.api


import com.pinto.github_user.data.model.ResponseDetailUser
import com.pinto.github_user.data.model.ResponseFollow
import com.pinto.github_user.data.model.ResponseSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun search(
        @Query("q") username: String
    ): Call<ResponseSearch>

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username: String
    ): Call<ResponseDetailUser>

    @GET("users/{username}/followers")
    fun followers(
        @Path("username") username: String
    ): Call<ArrayList<ResponseFollow>>

    @GET("users/{username}/following")
    fun following(
        @Path("username") username: String
    ): Call<ArrayList<ResponseFollow>>
}