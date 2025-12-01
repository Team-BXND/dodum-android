package com.example.dodum_android.feature.profile.changepw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.profile.password.PwChangeRequest
import com.example.dodum_android.network.profile.password.PwService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePwViewModel @Inject constructor(
    private val pwService: PwService
) : ViewModel() {

    private val _passwordChangeResult = MutableLiveData<String>()
    val passwordChangeResult: LiveData<String> get() = _passwordChangeResult

    fun changePassword(email: String, newPwd: String, checkPwd: String) {
        if (newPwd != checkPwd) {
            _passwordChangeResult.value = "새 비밀번호가 일치하지 않습니다."
            return
        }

        viewModelScope.launch {
            try {
                val request = PwChangeRequest(
                    email = email,
                    newPassword = newPwd,
                    passwordCheck = checkPwd
                )
                val response = pwService.changePassword(request)

                if (response.isSuccessful) {
                    _passwordChangeResult.value = response.body()?.data ?: "성공"
                } else {
                    _passwordChangeResult.value = "비밀번호 변경 실패 (${response.code()})"
                }
            } catch (e: Exception) {
                _passwordChangeResult.value = "오류 발생: ${e.message}"
            }
        }
    }
}