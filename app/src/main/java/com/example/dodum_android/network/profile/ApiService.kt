package com.example.dodum_android.network.profile

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("profile/{id}")
    suspend fun getProfile(@Path("id") id: String): Response<Profile>

    @PUT("/profile/{id}")
    suspend fun updateProfile(
        @Path("id") id: String,
        @Body profile: Profile
    ): Response<UpdateProfileResponse>

    @GET("/profile")
    suspend fun getMyPosts(): Response<MyPostResponse>
}