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

    fun loadContestList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 페이지네이션이 있다면 page 파라미터 관리 필요. 일단 1페이지 고정.
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

    fun loadContestDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = contestService.getContestDetail(id)
                if (response.isSuccessful) {
                    _contestDetail.value = response.body()?.data
                } else {
                    Log.e("ContestVM", "Load detail failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ContestVM", "Load detail error", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createContest(
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
                // TODO: 이미지 업로드 로직 필요 (현재는 빈 문자열)
                val request = ContestCreateRequest(
                    title = title,
                    subtitle = "", // UI에 없으므로 빈값 처리
                    content = content,
                    email = email,
                    phone = phone,
                    time = time, // Date 형식 변환 필요시 처리
                    place = place,
                    image = ""
                )
                val response = contestService.createContest(request)
                if (response.isSuccessful) {
                    onSuccess()
                    loadContestList() // 목록 갱신
                } else {
                    onError("등록 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("오류 발생: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteContest(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = contestService.deleteContest(id)
                if (response.isSuccessful) {
                    onSuccess()
                    loadContestList() // 목록 갱신
                } else {
                    Log.e("ContestVM", "Delete failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ContestVM", "Delete error", e)
            }
        }
    }

    fun toggleAlert(id: Int) {
        viewModelScope.launch {
            try {
                val response = contestService.toggleContestAlert(id)
                if (response.isSuccessful && response.body()?.data?.success == true) {
                    // 성공 시 로컬 상태 업데이트 (낙관적 업데이트)
                    val currentDetail = _contestDetail.value
                    if (currentDetail != null && currentDetail.id == id) {
                        _contestDetail.value = currentDetail.copy(isAlertActive = !currentDetail.isAlertActive)
                    }
                    // 리스트 상태도 업데이트가 필요하면 여기서 처리
                }
            } catch (e: Exception) {
                Log.e("ContestVM", "Toggle alert error", e)
            }
        }
    }
}