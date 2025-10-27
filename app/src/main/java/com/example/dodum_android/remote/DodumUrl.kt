package com.example.dodum_android.remote

object DodumUrl {
    const val BASE_URL = "example"

    const val AUTH = "${BASE_URL}/auth"
    const val EMAIL = "${BASE_URL}/email"

    object Auth {
        const val SIGNIN = "${AUTH}/signin"
        const val SIGNUP = "${AUTH}/signup"

        const val PWCHANGE = "${AUTH}/pwchage"
        const val UNCHANGE = "${AUTH}/unchage"
    }

    object Email {
        const val SEND = "${EMAIL}/send"
        const val CHECK = "${EMAIL}/check"
    }
}
