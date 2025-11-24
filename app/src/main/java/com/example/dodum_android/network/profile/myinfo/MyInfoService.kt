package com.example.dodum_android.network.profile.myinfo

import com.example.dodum_android.network.DodumUrl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface MyInfoService {
    @GET(DodumUrl.Profile.GET_PROFILE)
    suspend fun getProfile(): Response<ProfileResponse>


    @PUT(DodumUrl.Profile.UPDATE_PROFILE)
    suspend fun updateProfile(
        @Body request: UpdateProfileRequest
    ): Response<UpdateProfileResponse>
}