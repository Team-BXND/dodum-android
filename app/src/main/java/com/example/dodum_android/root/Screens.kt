package com.example.dodum_android.root

sealed class Screens(val route: String) {
    object MyInform : Screens("myinform")
    object ChangeInform : Screens("changeinform")
    object ChangePw : Screens("changepw")
    object Profile: Screens("profile")
    object MyPosts: Screens("myposts")
}