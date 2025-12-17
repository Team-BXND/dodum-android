package com.example.dodum_android.feature.archive.write

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.network.archive.ArchiveModifyRequest
import com.example.dodum_android.network.archive.ArchiveService
import com.example.dodum_android.network.archive.ArchiveWriteRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ArchiveEditUiState(
    val title: String = "",
    val subtitle: String = "",
    val content: String = "",
    val category: String = "동아리",
    val teamName: String = "",
    val imageUrl: String? = null
)

@HiltViewModel
class ArchiveWriteViewModel @Inject constructor(
    private val archiveService: ArchiveService,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _editUiState = MutableStateFlow<ArchiveEditUiState?>(null)
    val editUiState = _editUiState.asStateFlow()

    // [Mock] 수정 모드 진입 시 기존 데이터 로드 흉내
    fun loadArchiveForEdit(archiveId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                delay(500) // 0.5초 로딩 지연

                // ================== [SERVER] 실제 서버 연결 코드 ==================
                /*
                val response = archiveService.getArchiveDetail(archiveId)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _editUiState.value = ArchiveEditUiState(
                            title = data.title,
                            subtitle = data.subtitle,
                            content = data.content,
                            category = "동아리", // API 응답에 category가 없다면 별도 처리 필요
                            teamName = data.teamname,
                            imageUrl = data.logoUrl
                        )
                    }
                }
                */
                // ================================================================

                // [MOCK] 수정할 더미 데이터 세팅
                _editUiState.value = ArchiveEditUiState(
                    title = "도담도담 (수정중)",
                    subtitle = "스마트 스쿨로 학교의 새로운 패러다임을 만듭니다.",
                    content = "이것은 불러온 더미 데이터입니다. 내용을 수정해보세요.",
                    teamName = "B1ND",
                    category = "동아리"
                )
                Log.d("ArchiveWriteViewModel", "더미 데이터 로드 완료")

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // [Mock] 작성 및 수정 제출 흉내
    fun submitArchive(
        archiveId: Long?, // null이면 작성, 값이 있으면 수정
        title: String,
        subtitle: String,
        content: String,
        category: String,
        teamName: String,
        imageUri: Uri?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (title.isBlank() || content.isBlank() || teamName.isBlank()) {
            onError("모든 필수 항목을 입력해주세요.")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            val userId = userRepository.getPublicIdSnapshot() ?: "unknown"
            val imageUrl = imageUri?.toString() ?: ""

            // 1.5초 동안 서버 전송 흉내
            delay(1500)

            try {
                if (archiveId == null) {
                    // *** 작성 (POST) ***

                    // ================== [SERVER] ==================
                    /*
                    val request = ArchiveWriteRequest(
                        userId = userId,
                        title = title,
                        subtitle = subtitle,
                        content = content,
                        thumbnail = imageUrl,
                        category = category,
                        teamname = teamName
                    )
                    val response = archiveService.writeArchive(request)
                    if (response.isSuccessful) onSuccess()
                    else onError("작성 실패: ${response.code()}")
                    */
                    // ==============================================

                    Log.d("ArchiveWriteViewModel", "📝 게시글 작성 성공 (Fake)")
                    onSuccess() // Mock success

                } else {
                    // *** 수정 (PATCH) ***

                    // ================== [SERVER] ==================
                    /*
                    val request = ArchiveModifyRequest(
                        archiveId = archiveId,
                        title = title,
                        subtitle = subtitle,
                        content = content,
                        category = category
                    )
                    val response = archiveService.modifyArchive(request)
                    if (response.isSuccessful) onSuccess()
                    else onError("수정 실패: ${response.code()}")
                    */
                    // ==============================================

                    Log.d("ArchiveWriteViewModel", "✏️ 게시글 수정 성공 (Fake) - ID: $archiveId")
                    onSuccess() // Mock success
                }
            } catch (e: Exception) {
                onError("에러 발생: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}