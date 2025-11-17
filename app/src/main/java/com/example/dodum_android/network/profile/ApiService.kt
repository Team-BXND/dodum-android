package com.example.dodum_android.network.profile

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("profile/{id}")
    suspend fun getProfile(@Path("id") id: Int): Response<ProfileResponse>


    @PUT("profile/{id}")
    suspend fun updateProfile(
        @Path("id") id: Int,
        @Body profile: Profile
    ): Response<UpdateProfileResponse>

    @GET("profile")
    suspend fun getMyPosts(): Response<MyPostResponse>
}