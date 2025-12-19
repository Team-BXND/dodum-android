package com.example.dodum_android.feature.misc.misc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.misc.MiscDetailDto
import com.example.dodum_android.network.misc.MiscService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiscViewModel @Inject constructor(
    private val miscService: MiscService
) : ViewModel() {


    private val _detail = MutableStateFlow<MiscDetailDto?>(null)
    val detail: StateFlow<MiscDetailDto?> = _detail.asStateFlow()

    /* ---------- Detail ---------- */

    fun loadDetail(id: Int) {
        viewModelScope.launch {
            runCatching {
                miscService.getMiscDetail(id)
            }.onSuccess { response ->
                if (response.success) {
                    _detail.value = response.data
                }
            }
        }
    }

    /* ---------- Like ---------- */

    fun like(id: Int) {
        viewModelScope.launch {
            miscService.likeMisc(id)
        }
    }

    /* ---------- Approve / Disapprove ---------- */

    fun approve(id: Int) {
        viewModelScope.launch {
            miscService.approveMisc(id)
        }
    }

    fun disapprove(id: Int) {
        viewModelScope.launch {
            miscService.disapproveMisc(id)
        }
    }
}