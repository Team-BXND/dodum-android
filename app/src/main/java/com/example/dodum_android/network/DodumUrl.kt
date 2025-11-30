package com.example.dodum_android.network

import com.example.dodum_android.BuildConfig
import com.example.dodum_android.network.profile.falsepost.FalsePost

object DodumUrl {
    const val BASE_URL = BuildConfig.BASE_URL

    const val AUTH = "${BASE_URL}auth/"
    const val EMAIL = "${AUTH}email/"

    object Auth {
        const val SIGNIN = "${AUTH}signin"
        const val SIGNUP = "${AUTH}signup"
        const val SIGNOUT = "${AUTH}signout"
    }

    object Email {
        const val SEND = "${EMAIL}send"
        const val CHECK = "${EMAIL}check"
    }

    object Profile {
        const val GET_PROFILE = "profile"
        const val UPDATE_PROFILE = "profile"
        const val MYPOST = "profile"
        const val FALSEPOST ="info/false"
    }

    object Major {
        const val Major = "major-recommend"
    }
}