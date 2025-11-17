package com.example.dodum_android.network.profile

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchProfile(id: Int): Profile? {
        val response = apiService.getProfile(id)
        return if (response.isSuccessful) response.body()?.data else null
    }

    suspend fun updateProfile(id: Int, profile: Profile): Boolean {
        val response = apiService.updateProfile(id, profile)
        return response.isSuccessful && response.body()?.success == true
    }

    suspend fun getMyPosts(): List<MyPost>? {
        val response = apiService.getMyPosts()
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }
}
