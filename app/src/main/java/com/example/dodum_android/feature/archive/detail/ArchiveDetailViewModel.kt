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
                // [수정] 실제 서버 통신 활성화 (id 사용)
                val response = archiveService.getArchiveDetail(id = archiveId)
                if (response.isSuccessful) {
                    _detail.value = response.body()
                } else {
                    Log.e("ArchiveDetailViewModel", "Load Fail: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // [주석 처리됨] 더미 데이터
            /*
            _detail.value = ArchiveDetailData( ... )
            */
        }
    }

    fun deleteArchive(archiveId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // [수정] 실제 서버 통신 활성화
                val response = archiveService.deleteArchive(archiveId)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    Log.e("ArchiveDetailViewModel", "Delete Fail: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}