package com.example.dodum_android.network.profile

import com.example.dodum_android.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object ProfileRepository {

    fun fetchProfile(id: String, onResult: (Profile?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.apiService.getProfile(id)
            if (response.isSuccessful) {
                onResult(response.body())
            } else {
                onResult(null)
            }
        }
    }

    fun updateProfile(id: String, profile: Profile, onResult: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.apiService.updateProfile(id, profile)
            onResult(response.isSuccessful && response.body()?.success == true)
        }
    }

    fun fetchMyPosts(onResult: (List<Post>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.apiService.getMyPosts()
            onResult(if (response.isSuccessful) response.body() else null)
        }
    }
}