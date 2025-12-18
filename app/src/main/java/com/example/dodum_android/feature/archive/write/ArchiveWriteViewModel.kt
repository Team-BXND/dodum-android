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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri

// 일회성 이벤트를 위한 Sealed Class
sealed class ArchiveWriteEvent {
    data class ShowToast(val message: String) : ArchiveWriteEvent()
    object NavigateBack : ArchiveWriteEvent() // 네비게이션 스택에서 뒤로 가기
    // object NavigateToDetail : ArchiveWriteEvent() // 게시글 상세 페이지로 이동
}

data class ArchiveEditUiState(
    val title: String = "",
    val subtitle: String? = null, // String?로 변경
    val content: String = "",
    val category: String = "미니프로젝트",
    val teamName: String? = null, // String?로 변경
    val thumbnailUrl: String? = null // 변수명 변경 (imageUri와의 혼동 방지)
)

@HiltViewModel
class ArchiveWriteViewModel @Inject constructor(
    private val archiveService: ArchiveService,
    private val userRepository: UserRepository // 사용되지 않는다면 제거 가능
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _editUiState = MutableStateFlow<ArchiveEditUiState?>(null)
    val editUiState: StateFlow<ArchiveEditUiState?> = _editUiState.asStateFlow()

    // 일회성 이벤트를 위한 SharedFlow
    private val _event = MutableSharedFlow<ArchiveWriteEvent>()
    val event: SharedFlow<ArchiveWriteEvent> = _event.asSharedFlow()

    // UI 상태 (필수)
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _subtitle = MutableStateFlow("")
    val subtitle: StateFlow<String> = _subtitle.asStateFlow()

    private val _teamName = MutableStateFlow("")
    val teamName: StateFlow<String> = _teamName.asStateFlow()

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    private val _selectedCategory = MutableStateFlow("동아리")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri.asStateFlow()

    fun onTitleChange(newTitle: String) { _title.value = newTitle }
    fun onSubtitleChange(newSubtitle: String) { _subtitle.value = newSubtitle }
    fun onTeamNameChange(newTeamName: String) { _teamName.value = newTeamName }
    fun onContentChange(newContent: String) { _content.value = newContent }
    fun onCategorySelect(category: String) { _selectedCategory.value = category }
    fun onImageSelect(uri: Uri?) { _selectedImageUri.value = uri }
    fun onImageRemove() { _selectedImageUri.value = null }


    fun loadArchiveForEdit(archiveId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = archiveService.getArchiveDetail(id = archiveId)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        // 각 상태 개별 업데이트
                        _title.value = data.title
                        _subtitle.value = data.subtitle ?: ""
                        _content.value = data.content // 이 값이 Screen의 TextFieldValue로 가야함
                        _teamName.value = data.teamname ?: ""
                        _selectedCategory.value = data.category

                        data.thumbnail?.let { url ->
                             _selectedImageUri.value = url.toUri()
                        }
                    }
                } else {
                    _event.emit(ArchiveWriteEvent.ShowToast("게시글 로드 실패: ${response.code()}"))
                }
            } catch (e: Exception) {
                _event.emit(ArchiveWriteEvent.ShowToast("에러 발생: ${e.message}"))
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun submitArchive(archiveId: Long?) {
        val currentTitle = _title.value
        val currentSubtitle = _subtitle.value
        val currentContent = _content.value
        val currentCategory = _selectedCategory.value
        val currentTeamName = _teamName.value
        val currentImageUri = _selectedImageUri.value

        if (currentTitle.isBlank() || currentContent.isBlank() || currentCategory.isBlank()) {
            viewModelScope.launch { _event.emit(ArchiveWriteEvent.ShowToast("제목, 내용, 카테고리는 필수 항목입니다.")) }
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // TODO: 실제 이미지 업로드 로직 구현 후 서버에서 받은 URL 사용하거나 파일
                val thumbnailUrl = currentImageUri?.toString() ?: "" // 임시
                Log.d("ArchiveWriteViewModel", "thumbnailUrl to send: $thumbnailUrl")

                val response = if (archiveId == null) {
                    // *** 작성 (POST) ***
                    val request = ArchiveWriteRequest(
                        title = currentTitle,
                        subtitle = currentSubtitle.ifBlank { null }, // 빈 문자열이면 null로 전송
                        content = currentContent,
                        thumbnail = thumbnailUrl.ifBlank { null }, // 빈 문자열이면 null로 전송
                        category = currentCategory,
                        teamname = currentTeamName.ifBlank { null } // 빈 문자열이면 null로 전송
                    )
                    archiveService.writeArchive(request)
                } else {
                    // *** 수정 (PATCH) ***
                    val request = ArchiveModifyRequest(
                        archiveId = archiveId,
                        title = currentTitle,
                        subtitle = currentSubtitle.ifBlank { null }, // 빈 문자열이면 null로 전송
                        content = currentContent,
                        category = currentCategory,
                        thumbnail = thumbnailUrl.ifBlank { null }, // 빈 문자열이면 null로 전송
                        teamname = currentTeamName.ifBlank { null } // 빈 문자열이면 null로 전송
                    )
                    archiveService.modifyArchive(request)
                }

                if (response.isSuccessful) {
                    val bodyString = response.body()?.toString()
                    Log.d("ArchiveWriteViewModel", "서버 응답: $bodyString")
                    _event.emit(ArchiveWriteEvent.ShowToast(if (archiveId == null) "게시글 작성 완료" else "게시글 수정 완료"))
                    _event.emit(ArchiveWriteEvent.NavigateBack)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러"
                    Log.e("ArchiveWriteViewModel", "API Error: ${response.code()}, $errorBody")
                    _event.emit(ArchiveWriteEvent.ShowToast("작성/수정 실패: ${response.code()} $errorBody"))
                }
            } catch (e: Exception) {
                Log.e("ArchiveWriteViewModel", "Network Error", e)
                _event.emit(ArchiveWriteEvent.ShowToast("네트워크 에러: ${e.message}"))
            } finally {
                _isLoading.value = false
            }
        }
    }
}