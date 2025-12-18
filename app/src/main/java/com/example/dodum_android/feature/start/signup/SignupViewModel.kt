package com.example.dodum_android.feature.start.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.network.start.email.EmailCheckRequest
import com.example.dodum_android.network.start.email.EmailSendErrorResponse
import com.example.dodum_android.network.start.email.EmailSendRequest
import com.example.dodum_android.network.start.email.EmailService
import com.example.dodum_android.network.start.signup.SignupRequest
import com.example.dodum_android.network.start.signup.SignupResponse
import com.example.dodum_android.network.start.signup.SignupService
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor (
    private val signupService: SignupService,
    private val emailService: EmailService,
    private val userRepository: UserRepository
) : ViewModel() {

    init {
        // ★ 이 로그가 화면 넘길 때마다 뜨면 -> 뷰모델이 계속 새로 만들어진다는 증거입니다.
        // 제대로 고치면 처음에 딱 한 번만 떠야 합니다.
        println("SignupViewModel Created: $this")
    }


//    username:String
//    Password:String
//    PasswordCheck:String
//    grade:int
//    class_no:int
//    student_no:int
//    phone:String
//    email:String
//    major:String
//    history:String
//    club:”BIND”|”3D”|”두카미”|”Louter”|”CNS”|”모디”|”ALT”|”Chatty”|”NONE”


    var form by mutableStateOf(SignupForm())
        internal set

    var signupSuccess by mutableStateOf<Boolean?>(null)
        private set

    var emailSuccess by mutableStateOf<Boolean?>(null)
        private set


    // [추가] 1단계: 아이디/비밀번호 저장
    fun updateIdPw(username: String, password: String) {
        form = form.copy(username = username, password = password)
    }

    // [추가] 2단계: 신상 정보 저장
    fun updateInfo(
        grade: String,
        classNo: Int,
        studentNo: Int,
        phone: String,
        club: String
    ) {
        val gradeInt = when (grade) {
            "1학년" -> 1
            "2학년" -> 2
            "3학년" -> 3
            else -> null
        }
        form = form.copy(
            grade = gradeInt,
            classNo = classNo,
            studentNo = studentNo,
            phone = phone,
            club = club.ifBlank { null }
        )
    }

    // [추가] 3단계: 이메일 저장
    fun updateEmail(email: String) {
        form = form.copy(email = email)
    }

    suspend fun signup(): Boolean {
        return try {
//            val request = SignupRequest(
//                username = form.username.toString(),
//                password = form.password.toString(),
//                grade = form.grade,
//                class_no = form.classNo,
//                student_no = form.studentNo,
//                phone = form.phone.toString(),
//                email = form.email.toString(),
//                major = form.major.toString(),
//                history = form.history.toString(),
//                club = form.club.toString()
//            )

            val request = SignupRequest(
                username = form.username,
                password = form.password,
                grade = form.grade,
                class_no = form.classNo,
                student_no = form.studentNo,
                phone = form.phone,
                email = form.email,
                major = form.major,
                history = form.history,
                club = form.club
            )

            val response: SignupResponse = signupService.signup(request)

            if (response.status == 200) {
                println("회원가입 성공: ${response.data}")
                signupSuccess = true
                true
            } else {
                println("회원가입 실패: ${response.data}")
                signupSuccess = false
                false
            }
        } catch (e: HttpException) {
            println("HTTP 오류: ${e.code()}")
            false
        } catch (e: IOException) {
            println("네트워크 오류: ${e.message}")
            false
        } catch (e: Exception) {
            println("예상치 못한 오류: ${e.message}")
            false
        }
    }


    suspend fun sendEmail(email: String): Boolean {
        return try {
            val request = EmailSendRequest(email)

            val response = emailService.sendEmail(request)

            if (response.isSuccessful) {
                val body = response.body()
                println("이메일 전송 성공: ${body?.data}")
                emailSuccess = true
                true
            } else {
                val errorJson = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorJson, EmailSendErrorResponse::class.java)

                println("이메일 전송 실패: code=${errorResponse.error.code}, message=${errorResponse.error.message}")

                emailSuccess = false
                false
            }
        } catch (e: HttpException) {
            println("HTTP 오류: ${e.code()}")
            false
        } catch (e: IOException) {
            println("네트워크 오류: ${e.message}")
            false
        } catch (e: Exception) {
            println("예상치 못한 오류: ${e.message}")
            false
        }
    }

    suspend fun checkEmail(email: String, authNum: String): Boolean {
        return try {
            val request = EmailCheckRequest(email, authNum)

            val response = emailService.checkEmail(request)

            if (response.isSuccessful) {
                val body = response.body()
                println("이메일 인증 성공: ${body?.data}")
                emailSuccess = true
                true
            } else {
                val errorBody = response.errorBody()?.string()
                println("이메일 인증 실패: $errorBody")
                emailSuccess = false
                false
            }
        } catch (e: HttpException) {
            println("HTTP 오류: ${e.code()}")
            false
        } catch (e: IOException) {
            println("네트워크 오류: ${e.message}")
            false
        } catch (e: Exception) {
            println("예상치 못한 오류: ${e.message}")
            false
        }
    }


}