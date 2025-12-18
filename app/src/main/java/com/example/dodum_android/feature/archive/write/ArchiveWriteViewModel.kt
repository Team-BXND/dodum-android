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
    private val userRepository: UserRepository
) : ViewModel() {

    // ... (StateFlow 변수들 기존과 동일)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _event = MutableSharedFlow<ArchiveWriteEvent>()
    val event: SharedFlow<ArchiveWriteEvent> = _event.asSharedFlow()

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

    // ... (onChange 함수들 기존과 동일)
    fun onTitleChange(newTitle: String) { _title.value = newTitle }
    fun onSubtitleChange(newSubtitle: String) { _subtitle.value = newSubtitle }
    fun onTeamNameChange(newTeamName: String) { _teamName.value = newTeamName }
    fun onContentChange(newContent: String) { _content.value = newContent }
    fun onCategorySelect(category: String) { _selectedCategory.value = category }
    fun onImageSelect(uri: Uri?) { _selectedImageUri.value = uri }
    fun onImageRemove() { _selectedImageUri.value = null }


    // [핵심 수정] 데이터를 불러오고 -> 그대로 다시 보내서 권한 체크
    fun loadArchiveForEdit(archiveId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. 일단 서버에서 기존 데이터를 가져옵니다.
                val response = archiveService.getArchiveDetail(id = archiveId)

                if (response.isSuccessful && response.body() != null) {
                    val originData = response.body()!!

                    // 2. [권한 확인용] 가져온 원본 데이터를 그대로 담아 수정 요청(PATCH)을 보내봅니다.
                    //    - 권한이 있다면: 성공(200)하고 내용은 그대로 유지됨 (안전)
                    //    - 권한이 없다면: 실패(403/401/404)하고 catch/else로 빠짐
                    val checkRequest = ArchiveModifyRequest(
                        archiveId = originData.id,
                        title = originData.title,
                        subtitle = originData.subtitle,
                        content = originData.content,
                        category = originData.category,
                        teamname = originData.teamname,
                        thumbnail = originData.thumbnail
                    )

                    val checkResponse = archiveService.modifyArchive(checkRequest)

                    if (checkResponse.isSuccessful) {
                        // 3. 권한 확인 통과! 이제 화면에 데이터를 뿌려줍니다.
                        _title.value = originData.title
                        _subtitle.value = originData.subtitle ?: ""
                        _content.value = originData.content
                        _teamName.value = originData.teamname ?: ""
                        _selectedCategory.value = originData.category
                        originData.thumbnail?.let { url ->
                            _selectedImageUri.value = url.toUri()
                        }
                    } else {
                        // 4. 권한 없음 (서버가 거부함) -> 쫓아내기
                        Log.e("ArchiveWrite", "권한 체크 실패: ${checkResponse.code()}")
                        _event.emit(ArchiveWriteEvent.ShowToast("수정 권한이 없습니다."))
                        _event.emit(ArchiveWriteEvent.NavigateBack)
                    }
                } else {
                    _event.emit(ArchiveWriteEvent.ShowToast("게시글 로드 실패: ${response.code()}"))
                    _event.emit(ArchiveWriteEvent.NavigateBack)
                }
            } catch (e: Exception) {
                _event.emit(ArchiveWriteEvent.ShowToast("에러 발생: ${e.message}"))
                e.printStackTrace()
                _event.emit(ArchiveWriteEvent.NavigateBack)
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
                val thumbnailUrl = currentImageUri?.toString() ?: ""
                Log.d("ArchiveWriteViewModel", "thumbnailUrl to send: $thumbnailUrl")

                val response = if (archiveId == null) {
                    // 작성
                    val request = ArchiveWriteRequest(
                        title = currentTitle,
                        subtitle = currentSubtitle.ifBlank { null },
                        content = currentContent,
                        thumbnail = thumbnailUrl.ifBlank { null },
                        category = currentCategory,
                        teamname = currentTeamName.ifBlank { null }
                    )
                    archiveService.writeArchive(request)
                } else {
                    // 수정 (사용자가 내용을 바꾼 뒤 최종 요청)
                    val request = ArchiveModifyRequest(
                        archiveId = archiveId,
                        title = currentTitle,
                        subtitle = currentSubtitle.ifBlank { null },
                        content = currentContent,
                        category = currentCategory,
                        thumbnail = thumbnailUrl.ifBlank { null },
                        teamname = currentTeamName.ifBlank { null }
                    )
                    archiveService.modifyArchive(request)
                }

                if (response.isSuccessful) {
                    _event.emit(ArchiveWriteEvent.ShowToast(if (archiveId == null) "게시글 작성 완료" else "게시글 수정 완료"))
                    _event.emit(ArchiveWriteEvent.NavigateBack)
                } else {
                    val errorBody = response.errorBody()?.string() ?: ""
                    _event.emit(ArchiveWriteEvent.ShowToast("실패: ${response.code()} $errorBody"))
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