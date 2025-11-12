package com.example.dodum_android.feature.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.profile.MyPost
import com.example.dodum_android.network.profile.Profile
import com.example.dodum_android.network.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _profile = mutableStateOf<Profile?>(null)
    private val _myPosts = MutableStateFlow<List<MyPost>>(emptyList())

    val profile: State<Profile?> = _profile
    val myPosts: StateFlow<List<MyPost>> = _myPosts

    fun loadMockData() {
        val fakeProfile = Profile(
            username = "홍길동",
            studentId = "honggildong1234",
            grade = "1",
            class_no = "1",
            student_no = "01",
            phone = "010-1234-5678",
            email = "bind@example.com",
            club = "바인드"
        )

        val fakePosts = listOf(
            MyPost(
                title = "오늘의 개발 일지",
                date = "2025-11-10",
                image = "https://picsum.photos/200/200?random=1"
            ),
            MyPost(
                title = "안드로이드 Compose 공부 후기",
                date = "2025-11-09",
                image = "https://picsum.photos/200/200?random=2"
            ),
            MyPost(
                title = "도둠 앱 프로필 화면 완성!",
                date = "2025-11-08",
                image = "https://picsum.photos/200/200?random=3"
            ),
            MyPost(
                title = "서버 연결 테스트 성공 🎉",
                date = "2025-11-07",
                image = "https://picsum.photos/200/200?random=4"
            )
        )


        _profile.value = fakeProfile
        _myPosts.value = fakePosts
    }


    fun loadProfile(id: String) {
        viewModelScope.launch {
            _profile.value = repository.fetchProfile(id)
        }
    }

    fun loadMyPosts() {
        viewModelScope.launch {
            val posts = repository.getMyPosts()
            posts?.let {
                _myPosts.value = it.sortedByDescending { post ->
                    parseDate(post.date)
                }
            }

        }
    }

    private fun parseDate(dateString: String):Long{
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd'T'HH:mm:ss")
            val localDateTime = LocalDateTime.parse(dateString, formatter)
            localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        } catch (e: Exception){
            0L
        }
    }

    fun updateProfile(profileId: String, updatedProfile: Profile, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.updateProfile(profileId, updatedProfile)
            onResult(success)
        }
    }
}
