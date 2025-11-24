package com.example.dodum_android.feature.profile.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
): ViewModel() {

    private val _profile = mutableStateOf<Profile?>(null)
    private val _myPosts = MutableStateFlow<List<MyPost>>(emptyList())

    val profile: State<Profile?> = _profile
    val myPosts: StateFlow<List<MyPost>> = _myPosts


    fun loadProfile() {
        viewModelScope.launch {
            try {
                val response = myInfoService.getProfile()
                if (response.isSuccessful) {
                    _profile.value = response.body()?.data  // ProfileResponse -> Profile
                } else {
                    _profile.value = null
                }
            } catch (e: Exception) {
                _profile.value = null
            }
        }
    }

    fun loadMyPosts() {
        viewModelScope.launch {
            try {
                val response = myPostService.getMyPosts()  // MyPostService에서 가져온 Response
                val posts = if (response.isSuccessful) {
                    response.body()?.data ?: emptyList()  // MyPostResponse에서 data 추출
                } else {
                    emptyList()  // 실패 시 빈 리스트
                }
                _myPosts.value = posts.sortedByDescending { post ->
                    parseDate(post.date ?: "")  // 안전한 날짜 처리
                }
            } catch (e: Exception) {
                _myPosts.value = emptyList()  // 예외 발생 시 빈 리스트
            }
        }
    }


    private fun parseDate(dateString: String): Long {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDateTime = LocalDateTime.parse(dateString, formatter)
            localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        } catch (e: Exception) {
            0L
        }
    }

}