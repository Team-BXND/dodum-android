package com.example.dodum_android.feature.info.write

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.info.InfoPostRequest
import com.example.dodum_android.network.info.InfoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class WriteUiState(
    val title: String = "",
    val content: TextFieldValue = TextFieldValue(""),
    val isPreview: Boolean = false
)

@HiltViewModel
class IWriteViewModel @Inject constructor(
    private val infoService: InfoService
) : ViewModel() {

    private val _uiState = MutableStateFlow(WriteUiState())
    val uiState: StateFlow<WriteUiState> = _uiState

    /* ---------- UI State ---------- */

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateContent(content: TextFieldValue) {
        _uiState.update { it.copy(content = content) }
    }

    fun togglePreview() {
        _uiState.update { it.copy(isPreview = !it.isPreview) }
    }

    /* ---------- Request ---------- */

    fun toCreateRequest(): InfoPostRequest {
        val state = _uiState.value
        return InfoPostRequest(
            title = state.title,
            subtitle = "",
            content = state.content.text,
            image = extractFirstImage(state.content.text)
        )
    }

    private fun extractFirstImage(content: String): String {
        val regex = Regex("!\\[.*?]\\((.*?)\\)")
        return regex.find(content)?.groupValues?.get(1) ?: ""
    }

    /* ---------- Network ---------- */

    fun postInfo(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                infoService.postInfo(toCreateRequest())
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}