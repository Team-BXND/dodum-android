package com.example.dodum_android.network.start.signup

import com.example.dodum_android.network.start.signin.SigninData
import com.example.dodum_android.network.start.signin.SigninError

data class SignupResponse(
    val status: Int,
    val data: SigninData? = null,
)