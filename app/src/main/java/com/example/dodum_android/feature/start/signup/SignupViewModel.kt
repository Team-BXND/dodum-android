package com.example.dodum_android.feature.start.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.network.start.signup.SignupService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor (
    private val signupService: SignupService,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _signupSuccess = mutableStateOf<Boolean?>(null)
    val signupSuccess: State<Boolean?> = _signupSuccess

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

    private val _username = mutableStateOf("")
    private val _password = mutableStateOf("")

    private val _grade = mutableStateOf<Int?>(null)
    private val _class_no = mutableStateOf<Int?>(null)
    private val _student_no = mutableStateOf<Int?>(null)
    private val _phone = mutableStateOf("")
    private val _club = mutableStateOf("")

    private val _email = mutableStateOf("")

    private val _major = mutableStateOf("")
    private val _history = mutableStateOf("")

    fun updateIdPw(username: String, password: String) {
        _username.value = username
        _password.value = password
    }

    fun updateContactInfo(
        gradee: String, class_no: Int, student_no: Int, phone: String, club: String
    ) {
        val grade = if (gradee == "1학년") 1 else if (gradee == "2학년") 2 else 3

        _grade.value = grade
        _class_no.value = class_no
        _student_no.value = student_no
        _phone.value = phone
        _club.value = club
    }

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun signup() {
        /* 적어야함 */
    }

}