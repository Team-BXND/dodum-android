package com.example.dodum_android.feature.start.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.network.start.signin.SigninRequest
import com.example.dodum_android.network.start.signin.SigninResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.dodum_android.network.start.signin.SigninService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class SigninViewModel @Inject constructor (
    private val signinService: SigninService,
    private val userRepository: UserRepository
) : ViewModel() {

    var signinSuccess by mutableStateOf<Boolean?>(null)

    private val _signinState = MutableStateFlow<SigninStatus>(SigninStatus.Idle)
    val signinState: StateFlow<SigninStatus> = _signinState

    sealed class SigninStatus {
        object Idle : SigninStatus()
        object Success : SigninStatus()
        object Error : SigninStatus()
    }

    fun signin(username: String, password: String) {
        viewModelScope.launch {
            try {
                val request = SigninRequest(username, password)
                val response: SigninResponse = signinService.signin(request)

                when {
                    response.status == 200 && response.data != null -> {
                        println("로그인 성공: accessToken=${response.data.accessToken}, refreshToken=${response.data.refreshToken}")
                        signinSuccess = true
                        _signinState.value = SigninStatus.Success

                        userRepository.saveUserData(
                            publicId = username,
                            accessToken = response.data.accessToken,
                            refreshToken = response.data.refreshToken
                        )
                    }
                    response.error != null -> {
                        println("로그인 실패: ${response.error.message}")
                        signinSuccess = false
                        _signinState.value = SigninStatus.Error
                    }
                    else -> {
                        println("로그인 실패: 알 수 없는 오류 발생 (status=${response.status})")
                        signinSuccess = false
                        _signinState.value = SigninStatus.Error
                    }
                }
            } catch (e: HttpException) {
                println("HTTP 예외 발생: ${e.message}")
                signinSuccess = false
                _signinState.value = SigninStatus.Error
            } catch (e: IOException) {
                println("네트워크 오류: ${e.message}")
                signinSuccess = false
                _signinState.value = SigninStatus.Error
            } catch (e: Exception) {
                println("알 수 없는 오류: ${e.message}")
                signinSuccess = false
                _signinState.value = SigninStatus.Error
            }
        }
    }
}