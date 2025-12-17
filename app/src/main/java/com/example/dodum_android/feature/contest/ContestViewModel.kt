package com.example.dodum_android.feature.contest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.feature.profile.profile.GetRole
import com.example.dodum_android.network.contest.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ContestEditUiState(
    val title: String,
    val subTitle: String?, // ★ 수정 시 subtitle은 null일 수 있으므로 nullable
    val email: String,
    val phone: String,
    val time: String,
    val place: String,
    val imageUrl: String
)

@HiltViewModel
class ContestViewModel @Inject constructor(
    private val contestService: ContestService,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _contestList = MutableStateFlow<List<ContestData>>(emptyList())
    val contestList = _contestList.asStateFlow()

    private val _contestDetail = MutableStateFlow<ContestData?>(null)
    val contestDetail = _contestDetail.asStateFlow()

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole = _userRole.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _editUiState = MutableStateFlow<ContestEditUiState?>(null)
    val editUiState = _editUiState.asStateFlow()

    init {
        loadUserRole()
        loadContestList()
    }

    private fun loadUserRole() {
        viewModelScope.launch {
            val token = userRepository.getAccessTokenSnapshot()
            _userRole.value = token?.let { GetRole(it) }
        }
    }

    // ★ 수정: Int 타입 적용
    fun loadContestList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = contestService.getContestList(page = 1)
                if (response.isSuccessful) {
                    _contestList.value = response.body()?.data ?: emptyList()
                } else {
                    Log.e("ContestVM", "Load list failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ContestVM", "Load list error", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ★ 수정: Int 타입 적용
    fun loadContestDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = contestService.getContestDetail(id)
                if (response.isSuccessful) {
                    _contestDetail.value = response.body()?.data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ★ 수정: Int 타입 적용
    fun loadContestForEdit(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = contestService.getContestDetail(id)
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null) {
                        _editUiState.value = ContestEditUiState(
                            title = data.title,
                            subTitle = null, // 상세 조회에는 subtitle이 없음
                            email = data.email,
                            phone = data.phone,
                            time = data.time,
                            place = data.place,
                            imageUrl = data.image
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

    // ★ 수정: Int 타입 적용
    fun submitContest(
        contestId: Int?,
        title: String,
        email: String,
        phone: String,
        time: String,
        place: String,
        content: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (title.isBlank() || content.isBlank()) {
            onError("필수 항목을 입력해주세요.")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // TODO: 실제 이미지 업로드 로직 필요
                val imageUrl = ""

                if (contestId == null) {
                    // *** 작성 (POST) ***
                    val request = ContestCreateRequest(
                        title = title,
                        subtitle = "", // 명세에 있음
                        content = content,
                        email = email,
                        phone = phone,
                        time = time,
                        place = place,
                        image = imageUrl
                    )
                    val response = contestService.createContest(request)
                    if (response.isSuccessful) {
                        onSuccess()
                        loadContestList()
                    } else {
                        onError("등록 실패: ${response.code()}")
                    }
                } else {
                    // *** 수정 (PUT) ***
                    val request = ContestUpdateRequest(
                        title = title,
                        content = content,
                        email = email,
                        phone = phone,
                        time = time,
                        place = place,
                        image = imageUrl
                    )
                    val response = contestService.updateContest(contestId, request)
                    if (response.isSuccessful) {
                        onSuccess()
                        loadContestList()
                        loadContestDetail(contestId)
                    } else {
                        onError("수정 실패: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                onError("오류 발생: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ★ 수정: Int 타입 적용
    fun deleteContest(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = contestService.deleteContest(id)
                if (response.isSuccessful) {
                    onSuccess()
                    loadContestList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ★ 수정: Int 타입 적용
    fun toggleAlert(id: Int) {
        viewModelScope.launch {
            try {
                val response = contestService.toggleContestAlert(id)
                if (response.isSuccessful && response.body()?.data?.success == true) {
                    val currentDetail = _contestDetail.value
                    if (currentDetail != null && currentDetail.id == id) {
                        _contestDetail.value = currentDetail.copy(isAlertActive = !currentDetail.isAlertActive)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}