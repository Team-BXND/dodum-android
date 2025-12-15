package com.example.dodum_android.feature.profile.myinfo

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.profile.myinfo.MyInfoService
import com.example.dodum_android.network.profile.myinfo.Profile
import com.example.dodum_android.network.start.signout.SignOutRequest
import com.example.dodum_android.network.start.signout.SignOutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor(
    private val myInfoService: MyInfoService,
    private val authService: SignOutService
): ViewModel() {

    private val _profile = mutableStateOf<Profile?>(null)

    val profile: State<Profile?> = _profile

    fun SignOut(onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val username = profile.value?.username ?: ""
                val response = authService.signOut(SignOutRequest(username))
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    onResult(body.status == 200, body.data)
                } else {
                    onResult(false, "로그아웃 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                onResult(false, "로그아웃 오류: ${e.localizedMessage}")
            }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            try {
                Log.d("MyInfoViewModel", "프로필 불러오기 시작...")
                val response = myInfoService.getProfile()
                if (response.isSuccessful) {
                    _profile.value = response.body()?.data  // ProfileResponse -> Profile
                    Log.d("MyInfoViewModel", "프로필 불러오기 성공: ${_profile.value}")
                } else {
                    _profile.value = null
                    Log.e("MyInfoViewModel", "프로필 불러오기 실패: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                _profile.value = null
                Log.e("MyInfoViewModel", "프로필 불러오기 중 예외 발생", e)
            }
        }
    }
}
