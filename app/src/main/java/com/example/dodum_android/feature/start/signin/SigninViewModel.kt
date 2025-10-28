package com.example.dodum_android.feature.start.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.start.signin.SigninRequest
import com.example.dodum_android.network.start.signin.SigninResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.dodum_android.network.start.signin.SigninService

@HiltViewModel
class SigninViewModel @Inject constructor (
    private val loginService: SigninService,
//    private val userRepository: UserRepository
) : ViewModel() {

    private val _signinSuccess = mutableStateOf<Boolean?>(null)
    val signinSuccess: State<Boolean?> = _signinSuccess

    fun signin(username: String, password: String) {
        viewModelScope.launch {
            try {
                val request = SigninRequest(username, password)
                val response: SigninResponse = loginService.signin(request)

                when {
                    response.status == 200 && response.data != null -> {
                        println("로그인 성공: accessToken=${response.data.accessToken}, refreshToken=${response.data.refreshToken}")
                        _signinSuccess.value = true

//                        userRepository.saveUserData(
//                            publicId = username,
//                            token = response.token,
//                            isChecked = false
//                        )
                    }
                    response.error != null -> {
                        println("로그인 실패: ${response.error.message}")
                        _signinSuccess.value = false
                    }
                    else -> {
                        println("로그인 실패: 알 수 없는 오류 발생 (status=${response.status})")
                        _signinSuccess.value = false
                    }
                }
            } catch (e: HttpException) {
                println("HTTP 예외 발생: ${e.message}")
                _signinSuccess.value = false
            } catch (e: IOException) {
                println("네트워크 오류: ${e.message}")
                _signinSuccess.value = false
            } catch (e: Exception) {
                println("알 수 없는 오류: ${e.message}")
                _signinSuccess.value = false
            }
        }
    }
}