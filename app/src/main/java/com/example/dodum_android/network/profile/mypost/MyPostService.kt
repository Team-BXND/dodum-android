package com.example.dodum_android.network.profile.mypost

import com.example.dodum_android.network.DodumUrl
import retrofit2.Response
import retrofit2.http.GET

interface MyPostService {
    @GET(DodumUrl.Profile.MYPOST)
    suspend fun getMyPosts(): Response<MyPostResponse>
}