package com.example.dodum_android.network

import com.example.dodum_android.BuildConfig

object DodumUrl {
    const val BASE_URL = BuildConfig.BASE_URL

    const val AUTH = "${BASE_URL}auth/"
    const val EMAIL = "${AUTH}email/"

    object Auth {
        const val SIGNIN = "${AUTH}signin"
        const val SIGNUP = "${AUTH}signup"
    }

    object Email {
        const val SEND = "${EMAIL}send"
        const val CHECK = "${EMAIL}check"
    }

    object Profile {
        const val GET_PROFILE = "profile"
        const val UPDATE_PROFILE = "profile"
        const val MYPOST = "profile"
    }

    object Major {
        const val Major = "major-recommend"
    }
}