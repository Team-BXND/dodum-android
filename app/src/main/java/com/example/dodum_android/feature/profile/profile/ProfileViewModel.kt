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
import java.time.LocalDateTime
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
    private val _myPosts = MutableStateFlow<List<MyPost>>(emptyList())
    private val _userRole = mutableStateOf<String?>(null)

    val profile: State<Profile?> = _profile
    val myPosts: StateFlow<List<MyPost>> = _myPosts
    val userRole: State<String?> = _userRole

    init {
        viewModelScope.launch {
            try {
                val token = userRepository.getAccessTokenSnapshot()  // 저장된 토큰 가져오기
                _userRole.value = token?.let { GetRole(it) }
                Log.d("ProfileViewModel", "사용자 역할 로드 완료: ${_userRole.value}")
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "사용자 역할 로드 중 오류 발생", e)
            }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            try {
                Log.d("ProfileViewModel", "프로필 불러오기 시작...")
                val response = myInfoService.getProfile()
                if (response.isSuccessful) {
                    _profile.value = response.body()?.data
                    Log.d("ProfileViewModel", "프로필 불러오기 성공: ${_profile.value}")
                } else {
                    _profile.value = null
                    Log.e("ProfileViewModel", "프로필 불러오기 실패: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                _profile.value = null
                Log.e("ProfileViewModel", "프로필 불러오기 중 예외 발생", e)
            }
        }
    }

    fun loadMyPosts() {
        viewModelScope.launch {
            try {
                Log.d("ProfileViewModel", "내 게시글 불러오기 시작...")
                val response = myPostService.getMyPosts()
                val posts = if (response.isSuccessful) {
                    response.body()?.data ?: emptyList()
                } else {
                    Log.e("ProfileViewModel", "내 게시글 불러오기 실패: ${response.code()} ${response.message()}")
                    emptyList()
                }
                _myPosts.value = posts.sortedByDescending { post ->
                    parseDate(post.date ?: "")
                }
                Log.d("ProfileViewModel", "내 게시글 불러오기 완료: ${_myPosts.value.size}개 게시글")
            } catch (e: Exception) {
                _myPosts.value = emptyList()
                Log.e("ProfileViewModel", "내 게시글 불러오기 중 예외 발생", e)
            }
        }
    }


    private fun parseDate(dateString: String): Long {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDateTime = LocalDateTime.parse(dateString, formatter)
            localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        } catch (e: Exception) {
            Log.e("ProfileViewModel", "날짜 파싱 실패: $dateString", e)
            0L
        }
    }

}
