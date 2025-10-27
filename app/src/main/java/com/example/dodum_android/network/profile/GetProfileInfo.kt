package com.example.dodum_android.network.profile

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

data class Profile(
    val username: String,
    val studentId: String,
    val grade: String,
    val class_no: String,
    val student_no: String,
    val phone: String,
    val email: String,
    val club: String
)

data class Post(
    val title: String,
    val date: String
)

data class UpdateProfileResponse(
    val success: Boolean
)


interface ApiService {

    @GET("/profile/{id}")
    suspend fun getProfile(@Path("id") id: String): Response<Profile>

    @PUT("/profile/{id}")
    suspend fun updateProfile(
        @Path("id") id: String,
        @Body profile: Profile
    ): Response<UpdateProfileResponse>

    @GET("/profile")
    suspend fun getMyPosts(): Response<List<Post>>
}