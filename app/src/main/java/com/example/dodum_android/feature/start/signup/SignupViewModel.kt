package com.example.dodum_android.feature.start.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.network.start.email.EmailCheckRequest
import com.example.dodum_android.network.start.email.EmailSendRequest
import com.example.dodum_android.network.start.email.EmailService
import com.example.dodum_android.network.start.signup.SignupRequest
import com.example.dodum_android.network.start.signup.SignupResponse
import com.example.dodum_android.network.start.signup.SignupService
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
//
//    private val _username = mutableStateOf("")
//    private val _password = mutableStateOf("")
//
//    private val _grade = mutableStateOf<Int?>(null)
//    private val _class_no = mutableStateOf<Int?>(null)
//    private val _student_no = mutableStateOf<Int?>(null)
//    private val _phone = mutableStateOf("")
//    private val _club = mutableStateOf<String?>(null)
//
//    private val _email = mutableStateOf("")
//
//    private val _major = mutableStateOf<String?>(null)
//    private val _history = mutableStateOf<String?>(null)
//
//
//    fun updateIdPw(username: String, password: String) {
//        _username.value = username
//        _password.value = password
//    }
//
//    fun updateContactInfo(
//        gradee: String?, class_no: Int?, student_no: Int?, phone: String, club: String?
//    ) {
//        val grade = when (gradee) {
//            "1학년" -> 1
//            "2학년" -> 2
//            "3학년" -> 3
//            else -> null
//        }
//
//        _grade.value = grade
//        _class_no.value = class_no
//        _student_no.value = student_no
//        _phone.value = phone
//
//        _club.value = club?.ifBlank { null }
//    }
//
//    fun updateEmail(email: String) {
//        _email.value = email
//    }

    var form by mutableStateOf(SignupForm())
        internal set

    var signupSuccess by mutableStateOf<Boolean?>(null)
        private set

    var emailSuccess by mutableStateOf<Boolean?>(null)
        private set

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
                val errorBody = response.errorBody()?.string()
                println("이메일 전송 실패: $errorBody")
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