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

    fun loadArchiveForEdit(archiveId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // [수정] 서버 연결 활성화
                val response = archiveService.getArchiveDetail(id = archiveId)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _editUiState.value = ArchiveEditUiState(
                            title = data.title,
                            subtitle = data.subtitle.toString(),
                            content = data.content,
                            category = "동아리", // API 응답에 category가 없다면 별도 처리 필요
                            teamName = data.teamname.toString(),
                            imageUrl = data.thumbnail // thumbnail 매핑
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun submitArchive(
        archiveId: Long?,
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

            // [수정] userId 제거
            // TODO: 실제 이미지 업로드 후 URL 받아오는 로직 필요. 현재는 Uri String 전송.
            val imageUrl = imageUri?.toString() ?: ""

            try {
                if (archiveId == null) {
                    // *** 작성 (POST) ***
                    // [수정] userId 제거, thumbnail 추가
                    val request = ArchiveWriteRequest(
                        title = title,
                        subtitle = subtitle,
                        content = content,
                        thumbnail = imageUrl,
                        category = category,
                        teamname = teamName
                    )

                    // [수정] 서버 통신 활성화
                    val response = archiveService.writeArchive(request)
                    if (response.isSuccessful) onSuccess()
                    else onError("작성 실패: ${response.code()} ${response.message()}")

                } else {
                    // *** 수정 (PATCH) ***
                    val request = ArchiveModifyRequest(
                        archiveId = archiveId,
                        title = title,
                        subtitle = subtitle,
                        content = content,
                        category = category,
                        thumbnail = null,
                        teamname = teamName
                    )

                    // [수정] 서버 통신 활성화
                    val response = archiveService.modifyArchive(request)
                    if (response.isSuccessful) onSuccess()
                    else onError("수정 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("에러 발생: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}