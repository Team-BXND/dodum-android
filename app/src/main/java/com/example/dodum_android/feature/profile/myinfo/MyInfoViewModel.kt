package com.example.dodum_android.feature.profile.myinfo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.profile.myinfo.MyInfoService
import com.example.dodum_android.network.profile.myinfo.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor(
    private val myInfoService: MyInfoService,
): ViewModel() {

    private val _profile = mutableStateOf<Profile?>(null)

    val profile: State<Profile?> = _profile


    fun loadProfile() {
        viewModelScope.launch {
            try {
                val response = myInfoService.getProfile()
                if (response.isSuccessful) {
                    _profile.value = response.body()?.data  // ProfileResponse -> Profile
                } else {
                    _profile.value = null
                }
            } catch (e: Exception) {
                _profile.value = null
            }
        }
    }
}