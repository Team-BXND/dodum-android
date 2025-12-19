package com.example.dodum_android.feature.info.information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.info.InfoDetail
import com.example.dodum_android.network.info.InfoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val infoService: InfoService
) : ViewModel() {

    private val _detail = MutableStateFlow<InfoDetail?>(null)
    val detail = _detail.asStateFlow()

    fun loadDetail(id: Int) {
        viewModelScope.launch {
            runCatching {
                infoService.getInfoDetail(id)
            }.onSuccess {
                _detail.value = it.data
            }
        }
    }

    fun deleteInfo(
        id: Int,
        token: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            runCatching {
                infoService.deleteInfo(id, token)
            }.onSuccess {
                onSuccess()
            }
        }
    }

}