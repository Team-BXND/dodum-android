package com.example.dodum_android.feature.misc.write

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.dodum_android.network.misc.MiscCreateRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


enum class Category(
    val label: String,
    val serverKey: String
) {
    LECTURE("강의추천", "lecture"),
    TOOL("개발도구", "tool"),
    PLATFORM("플랫폼추천", "platform"),
    SCHOOL("학교지원", "school_support")
}

data class WriteUiState(
    val title: String = "",
    val content: TextFieldValue = TextFieldValue(""),
    val category: Category = Category.SCHOOL,
    val imageUri: Uri? = null,
    val isPreview: Boolean = false
)

@HiltViewModel
class MWriteViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(WriteUiState())
    val uiState: StateFlow<WriteUiState> = _uiState

    fun updateTitle(title: String) =
        _uiState.update { it.copy(title = title) }

    fun updateContent(content: TextFieldValue) =
        _uiState.update { it.copy(content = content) }

    fun selectCategory(category: Category) =
        _uiState.update { it.copy(category = category) }

    fun togglePreview() =
        _uiState.update { it.copy(isPreview = !it.isPreview) }

    fun setImage(uri: Uri?) =
        _uiState.update { it.copy(imageUri = uri) }

    /** 서버 요청 객체로 변환 */
    fun toCreateRequest(): MiscCreateRequest {
        val state = _uiState.value
        return MiscCreateRequest(
            title = state.title,
            category = state.category.serverKey,
            content = state.content.text
        )
    }

}