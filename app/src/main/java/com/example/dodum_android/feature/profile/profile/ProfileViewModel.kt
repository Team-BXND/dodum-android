package com.example.dodum_android.feature.profile.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.network.profile.myinfo.MyInfoService
import com.example.dodum_android.network.profile.myinfo.Profile
import com.example.dodum_android.network.profile.mypost.MyPost
import com.example.dodum_android.network.profile.mypost.MyPostService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val myInfoService: MyInfoService,
    private val myPostService: MyPostService,
    private val userRepository: UserRepository
): ViewModel() {

    private val _profile = mutableStateOf<Profile?>(null)
    val profile: State<Profile?> = _profile

    private val _myPosts = MutableStateFlow<List<MyPost>>(emptyList())
    val myPosts: StateFlow<List<MyPost>> = _myPosts

    private val _myPostCount = mutableStateOf(0)
    val myPostCount: State<Int> = _myPostCount

    private val _userRole = mutableStateOf<String?>(null)
    val userRole: State<String?> = _userRole

    init {
        viewModelScope.launch {
            val token = userRepository.getAccessTokenSnapshot()
            _userRole.value = token?.let { GetRole(it) }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            try {
                val response = myInfoService.getProfile()
                if (response.isSuccessful) {
                    _profile.value = response.body()?.data
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "프로필 로드 실패", e)
            }
        }
    }

    fun loadMyPosts() {
        viewModelScope.launch {
            try {
                val response = myPostService.getMyPosts()
                val posts = if (response.isSuccessful) {
                    response.body()?.data ?: emptyList()
                } else {
                    emptyList()
                }

                val sortedPosts = posts.sortedByDescending {
                    parseDate(it.date ?: "")
                }

                _myPosts.value = sortedPosts
                _myPostCount.value = sortedPosts.size   // ✅ 서버 기반 개수
            } catch (e: Exception) {
                _myPosts.value = emptyList()
                _myPostCount.value = 0
            }
        }
    }

    private fun parseDate(dateString: String): Long {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDate = LocalDate.parse(dateString, formatter)
            localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        } catch (e: Exception) {
            0L
        }
    }
}