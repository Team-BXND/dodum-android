package com.example.dodum_android.feature.archive.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.feature.profile.profile.GetRole
import com.example.dodum_android.network.archive.ArchiveDetailData
import com.example.dodum_android.network.archive.ArchiveService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
            _userRole.value = token?.let { GetRole(it) } // JWT 디코딩
        }
    }

    // [Mock] 상세 데이터 로드 흉내
    fun loadDetail(archiveId: Long) {
        viewModelScope.launch {
            _detail.value = null // 로딩 UI 표시를 위해 초기화
            delay(500) // 0.5초 로딩 지연

            // ================== [SERVER] 실제 서버 연결 코드 ==================
            /*
            try {
                val response = archiveService.getArchiveDetail(archiveId)
                if (response.isSuccessful) {
                    _detail.value = response.body()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            */
            // ================================================================

            // [MOCK] 더미 데이터 설정
            _detail.value = ArchiveDetailData(
                title = "도담도담",
                subtitle = "스마트 스쿨로 학교의 새로운 패러다임을 만듭니다.",
                teamname = "B1ND",
                content = "도담도담은 외출/외박 신청, 심야 자습 신청, 급식 확인, 기숙사 아침 기상송 확인, 학교와 기숙사 상벌점 조회, 퇴사 버스 신청, 학교 일정 조회 기능을 제공합니다.\n\n2019년 개발을 시작해 2020년 출시한 도담도담은 바인드의 대표적인 프로젝트입니다.",
                createdAt = "2024-03-20",
                logoUrl = null
            )
        }
    }

    // [Mock] 삭제 기능 흉내
    fun deleteArchive(archiveId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            Log.d("ArchiveDetailViewModel", "🗑️ 게시글 삭제 요청 중... (Fake) - ID: $archiveId")

            delay(1000) // 1초 삭제 로딩 지연

            // ================== [SERVER] 실제 서버 연결 코드 ==================
            /*
            try {
                val response = archiveService.deleteArchive(archiveId)
                if (response.isSuccessful) {
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            */
            // ================================================================

            Log.d("ArchiveDetailViewModel", "🗑️ 게시글 삭제 완료 (Fake)")

            // 무조건 성공 처리
            onSuccess()
        }
    }
}