package com.example.dodum_android.feature.archive.write

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.network.archive.ArchiveService
import com.example.dodum_android.network.archive.ArchiveWriteRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveWriteViewModel @Inject constructor(
    private val archiveService: ArchiveService,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    val publicIdFlow = userRepository.publicIdFlow

    fun uploadArchive(
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
            try {
                // TODO: 실제 이미지 업로드 로직 필요 (Uri -> Server URL)
                val imageUrl = imageUri?.toString() ?: ""

                val request = ArchiveWriteRequest(
                    userId = publicIdFlow.toString(),
                    title = title,
                    subtitle = subtitle,
                    content = content,
                    thumbnail = imageUrl,
                    category = category,
                    teamname = teamName
                )

                // Server API Call
                val response = archiveService.writeArchive(request)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("업로드 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("에러 발생: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}