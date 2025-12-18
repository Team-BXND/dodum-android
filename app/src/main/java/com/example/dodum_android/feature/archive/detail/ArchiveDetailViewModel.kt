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
                    // [수정] .body()?.data 가 아니라 .body() 자체가 데이터임
                    _detail.value = response.body()
                    Log.d("ArchiveDetail", "데이터 로드 성공: ${response.body()}")
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
                // [수정] Service가 Map을 받도록 수정했으므로 맞춰서 전달
                val requestBody = mapOf("archiveId" to archiveId)

                val response = archiveService.deleteArchive(requestBody)

                // status가 String인지 Int인지 명세서/응답 확인 필요 (여기서는 기존 코드 유지하되 로그 강화)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    val errorString = response.errorBody()?.string()
                    Log.e("ArchiveDetail", "삭제 실패: ${response.code()} / $errorString")
                }
            } catch (e: Exception) {
                Log.e("ArchiveDetail", "네트워크 에러", e)
            }
        }
    }

}