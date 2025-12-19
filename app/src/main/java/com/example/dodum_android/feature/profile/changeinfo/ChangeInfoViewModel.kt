package com.example.dodum_android.feature.profile.changeinfo

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.profile.myinfo.MyInfoService
import com.example.dodum_android.network.profile.myinfo.Profile
import com.example.dodum_android.network.profile.myinfo.UpdateProfileRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeInfoViewModel @Inject constructor(
    private val myInfoService: MyInfoService,
): ViewModel() {

    private val _profile = mutableStateOf<Profile?>(null)

    val profile: State<Profile?> = _profile


    fun updateProfile(
        id: Int,
        grade: Int,
        classNo: Int,
        studentNo: Int,
        phone: String,
        email: String,
        club: String
    ) {
        viewModelScope.launch {
            try {
                val request = UpdateProfileRequest(
                    grade = grade,
                    class_no = classNo,
                    student_no = studentNo,
                    phone = phone,
                    email = email,
                    club = club
                )
                val response = myInfoService.updateProfile(id, request)

                if (response.isSuccessful) {
                    val message = response.body()?.data ?: "수정 성공"
                    // 필요하면 메시지를 LiveData/StateFlow로 노출
                    Log.d("ProfileUpdate", "성공: $message")
                } else {
                    Log.e("ProfileUpdate", "실패: ${response.code()}")
                }

            } catch (e: Exception) {
                Log.e("ProfileUpdate", "에러 발생", e)
            }
        }
    }

    fun refreshProfile(id: Int) {
        viewModelScope.launch {
            try {
                val response = myInfoService.getProfile(id)
                if (response.isSuccessful) {
                    _profile.value = response.body()?.data
                }
            } catch (e: Exception) {
                Log.e("Profile", "프로필 조회 실패", e)
            }
        }
    }
}