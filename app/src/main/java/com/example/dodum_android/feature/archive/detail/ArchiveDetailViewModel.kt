package com.example.dodum_android.feature.archive.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.feature.profile.profile.GetRole
import com.example.dodum_android.network.archive.ArchiveDetailData
import com.example.dodum_android.network.archive.ArchiveService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveDetailViewModel @Inject constructor(
    private val archiveService: ArchiveService,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _detail = MutableStateFlow<ArchiveDetailData?>(null)
    val detail = _detail.asStateFlow()

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole = _userRole.asStateFlow()

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId = _currentUserId.asStateFlow()

    init {
        loadUserRole()
    }

    private fun loadUserRole() {
        viewModelScope.launch {
            val token = userRepository.getAccessTokenSnapshot()
            val userId = userRepository.getPublicIdSnapshot()
            _currentUserId.value = userId
            _userRole.value = token?.let { GetRole(it) }
        }
    }

    fun loadDetail(archiveId: Long) {
        viewModelScope.launch {
            try {
                val response = archiveService.getArchiveDetail(id = archiveId)
                if (response.isSuccessful) {
                    // [수정] Response Wrapper에서 data 필드를 추출
                    _detail.value = response.body()?.data
                } else {
                    Log.e("ArchiveDetailViewModel", "Load Fail: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteArchive(archiveId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = archiveService.deleteArchive(archiveId = archiveId)
                // 응답 성공 및 status 확인 (명세서에 status는 String임)
                if (response.isSuccessful && response.body()?.status == "success") {
                    onSuccess()
                } else {
                    Log.e("ArchiveDetail", "삭제 실패: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ArchiveDetail", "네트워크 에러", e)
            }
        }
    }
}