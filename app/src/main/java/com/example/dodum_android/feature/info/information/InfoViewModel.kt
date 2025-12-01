package com.example.dodum_android.feature.info.information

import androidx.lifecycle.ViewModel
import com.example.dodum_android.network.info.InfoService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val infoService: InfoService
) : ViewModel() {

}