package com.example.dodum_android.feature.archive

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.archive.ArchiveItem
import com.example.dodum_android.network.archive.ArchiveModifyRequest
import com.example.dodum_android.network.archive.ArchiveService
import com.example.dodum_android.network.archive.ArchiveWriteRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val archiveService: ArchiveService
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("동아리")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _archiveList = MutableStateFlow<List<ArchiveItem>>(emptyList())
    val archiveList: StateFlow<List<ArchiveItem>> = _archiveList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        // ViewModel이 생성될 때 초기 데이터 로드
        fetchArchives(_selectedCategory.value)
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        fetchArchives(category)
    }

    // [수정] public으로 변경하여 ArchiveScreen에서 명시적으로 호출 가능
    fun fetchArchives(category: String? = null) { // category가 null이면 현재 선택된 카테고리 사용
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val categoryToFetch = category ?: _selectedCategory.value
                val response = archiveService.getArchiveList(categoryToFetch)
                if (response.isSuccessful) {
                    _archiveList.value = response.body() ?: emptyList()
                    Log.d("ArchiveViewModel", "Loaded: ${response.body()}")
                } else {
                    Log.e("ArchiveViewModel", "Failed: ${response.code()}")
                    _archiveList.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("ArchiveViewModel", "Error", e)
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}