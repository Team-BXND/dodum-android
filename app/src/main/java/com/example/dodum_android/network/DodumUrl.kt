package com.example.dodum_android.network

import com.example.dodum_android.BuildConfig

object DodumUrl {
    const val BASE_URL = BuildConfig.BASE_URL

    const val AUTH = "${BASE_URL}auth/"
    const val EMAIL = "${AUTH}email/"
    const val INFO = "${BASE_URL}info/"
    const val MISC = "${BASE_URL}misc/"
    const val ARCHIVE = "${BASE_URL}archive/"

    object Auth {
        const val SIGNIN = "${AUTH}signin"
        const val SIGNUP = "${AUTH}signup"
        const val PW = "${AUTH}pwchange"
        const val SIGNOUT = "${AUTH}signout"
    }

    object Email {
        const val SEND = "${EMAIL}send"
        const val CHECK = "${EMAIL}check"
    }

    object Profile {
        const val GET_PROFILE = "profile"
        const val UPDATE_PROFILE = "profile"
        const val MYPOST = "profile/write"
        const val FALSEPOST = "info/false"

    }

    object Info {
        const val GET_INFO = INFO
        const val INFO_ID = "${INFO}{id}"
        const val INFO_CATEGORY = "${INFO}{category}"
        const val LIKE = "${INFO}{id}/like"
        const val APPROVE = "${INFO}{id}/approve"
        const val DISAPPROVE = "${INFO}{id}/disapprove"
    }

    object Major {
        const val Major = "major-recommend"
    }

    object Misc {
        const val BASE = "${MISC}"
        const val MISC_ID = "${MISC}{id}"
        const val MISC_LIKE = "${MISC}{id}/like"
        const val MISC_APPROVE = "${MISC}{id}/approve"
        const val MISC_DISAPPROVE = "${MISC}{id}/disapprove"
    }

    object Archive {
        const val WRITE = "${ARCHIVE}write"
        const val ALL = "${ARCHIVE}all"
        const val DETAIL = "${ARCHIVE}{id}"
        const val MODIFY = "${ARCHIVE}"
        const val DELETE = "${ARCHIVE}"
    }

    object Contest {
        const val BASE = "${BASE_URL}contest"
        const val ID = "${BASE}/{id}"
        const val ACTIVE = "${ID}/active"
    }
}