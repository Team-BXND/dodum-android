package com.example.dodum_android.feature.start.signup

data class SignupForm(
    val username: String = "",
    val password: String = "",
    val grade: Int? = null,
    val classNo: Int? = null,
    val studentNo: Int? = null,
    val phone: String = "",
    val email: String = "",
    val major: String? = null,
    val history: String? = null,
    val club: String? = null
)