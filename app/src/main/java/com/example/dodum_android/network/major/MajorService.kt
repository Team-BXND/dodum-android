package com.example.dodum_android.network.major

import com.example.dodum_android.network.DodumUrl
import retrofit2.http.Body
import retrofit2.http.POST

interface MajorService {
    @POST(DodumUrl.Major.Major)
    suspend fun recommendMajor(
        @Body request: MajorRecommendRequest
    ): MajorRecommendResponse
}