package com.example.dodum_android.feature.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.profile.Post
import com.example.dodum_android.network.profile.Profile
import com.example.dodum_android.network.profile.ProfileRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ProfileViewModel : ViewModel() {
    val profile = mutableStateOf<Profile?>(null)
    val posts = mutableStateOf<List<Post>>(emptyList())


    fun loadProfile(profileId: String) {
        viewModelScope.launch {
            ProfileRepository.fetchProfile(profileId) { fetchedProfile ->
                profile.value = fetchedProfile
            }
        }
    }

    fun loadPosts() {
        viewModelScope.launch {
            ProfileRepository.fetchMyPosts { fetchedPosts ->
                posts.value = fetchedPosts ?: emptyList()
            }
        }
    }

    fun updateProfile(profileId: String, updatedProfile: Profile, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            ProfileRepository.updateProfile(profileId, updatedProfile) { success ->
                onResult(success)
            }
        }
    }

}