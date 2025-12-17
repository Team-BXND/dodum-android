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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val archiveService: ArchiveService
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("동아리")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _archiveList = MutableStateFlow<List<ArchiveItem>>(emptyList())
    val archiveList = _archiveList.asStateFlow()

    // [수정] _isLoading 상태 추가
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        // 초기 데이터 로드 (서버)
        fetchArchives("동아리")
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        fetchArchives(category)
    }

    // 실제 API 연동 함수
    private fun fetchArchives(category: String) {
        viewModelScope.launch {
            _isLoading.value = true // 로딩 시작
            try {
                val response = archiveService.getArchiveList(category)
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
                _isLoading.value = false // 로딩 종료
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
        if (title.isBlank() || content.isBlank() || category.isBlank()) {
            onError("필수 항목(제목, 내용, 카테고리)을 입력해주세요.")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true

            // Uri를 String으로 변환 (실제로는 서버에 업로드 후 URL을 받아야 할 수 있음)
            // 여기선 API 명세상 String으로 보내도록 되어 있다고 가정하고 toString() 사용
            // 만약 서버가 null을 허용하지 않고 빈 문자열을 원한다면 ?: "" 처리 필요
            val imageUrl = imageUri?.toString() ?: ""

            try {
                if (archiveId == null) {
                    // *** 작성 (POST) ***
                    val request = ArchiveWriteRequest(
                        title = title,
                        subtitle = subtitle.ifBlank { "" }, // String 타입이면 null 대신 "" 권장될 수 있음. 서버 스펙 확인 필요
                        content = content,
                        thumbnail = imageUrl,
                        category = category,
                        teamname = teamName.ifBlank { "" }
                    )

                    val response = archiveService.writeArchive(request)
                    if (response.isSuccessful) onSuccess()
                    else onError("작성 실패: ${response.code()}")

                } else {
                    // *** 수정 (PATCH) ***
                    // ArchiveModifyRequest의 필드가 nullable인지 확인 필요 (보통 수정 시 null이면 기존 값 유지)
                    // 여기서는 질문 코드의 로직을 따름
                    val request = ArchiveModifyRequest(
                        archiveId = archiveId,
                        title = title,
                        subtitle = subtitle.ifBlank { "" },
                        content = content,
                        // thumbnail = imageUrl, // ArchiveModifyRequest에 thumbnail 필드가 있다면 주석 해제
                        category = category,
                        thumbnail = null,
                        teamname = teamName,
                        // teamname = teamName.ifBlank { null } // ArchiveModifyRequest에 teamname 필드가 있다면 주석 해제
                    )

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

//    private fun loadMockData(category: String) {
//        val mockData = when (category) {
//            "동아리" -> listOf(
//                ArchiveItem(
//                    id = 1,
//                    title = "도담도담",
//                    teamname = "바인드",
//                    category = "동아리",
//                    description =
//                        "도담도담은 외출/외박 신청, 심야 자습 신청, 급식 확인, 기숙사 아침 기상송 확인, 학교와 기숙사 상벌점 조회, 퇴사 버스 신청, 학교 일정 조회 기능을 제공합니다.",
//                ),
//                ArchiveItem(
//                    id = 2,
//                    title = "도담도담",
//                    teamname = "바인드",
//                    category = "동아리",
//                    description =
//                        "도담도담은 외출/외박 신청, 심야 자습 신청, 급식 확인, 기숙사 아침 기상송 확인, 학교와 기숙사 상벌점 조회, 퇴사 버스 신청, 학교 일정 조회 기능을 제공합니다.",
//                )
//            )
//            "나르샤" -> listOf(
//                ArchiveItem(
//                    id = 3,
//                    title = "나르샤 제목",
//                    teamname = "제작자",
//                    category = "나르샤",
//                    description =
//                        "도담도담은 외출/외박 신청, 심야 자습 신청, 급식 확인, 기숙사 아침 기상송 확인, 학교와 기숙사 상벌점 조회, 퇴사 버스 신청, 학교 일정 조회 기능을 제공합니다.",
//                ),
//                ArchiveItem(
//                    id = 4,
//                    title = "나르샤 제목",
//                    teamname = "제작자",
//                    category = "나르샤",
//                    description =
//                        "도담도담은 외출/외박 신청, 심야 자습 신청, 급식 확인, 기숙사 아침 기상송 확인, 학교와 기숙사 상벌점 조회, 퇴사 버스 신청, 학교 일정 조회 기능을 제공합니다.",
//                )
//            )
//            "대회 수상작" -> listOf(
//                ArchiveItem(
//                    id = 5,
//                    title = "프로젝트 명",
//                    teamname = "수상종류",
//                    category = "대회 수상작",
//                    description =
//                        "도담도담은 외출/외박 신청, 심야 자습 신청, 급식 확인, 기숙사 아침 기상송 확인, 학교와 기숙사 상벌점 조회, 퇴사 버스 신청, 학교 일정 조회 기능을 제공합니다.",
//                ),
//                ArchiveItem(
//                    id = 6,
//                    title = "프로젝트 명",
//                    teamname = "수상종류",
//                    category = "대회 수상작",
//                    description =
//                        "도담도담은 외출/외박 신청, 심야 자습 신청, 급식 확인, 기숙사 아침 기상송 확인, 학교와 기숙사 상벌점 조회, 퇴사 버스 신청, 학교 일정 조회 기능을 제공합니다.",
//                )
//            )
//            "미니 프로젝트" -> listOf(
//                ArchiveItem(
//                    id = 7,
//                    title = "프로젝트 명",
//                    teamname = "제작연도",
//                    category = "미니 프로젝트",
//                    description =
//                        "도담도담은 외출/외박 신청, 심야 자습 신청, 급식 확인, 기숙사 아침 기상송 확인, 학교와 기숙사 상벌점 조회, 퇴사 버스 신청, 학교 일정 조회 기능을 제공합니다.",
//                ),
//                ArchiveItem(
//                    id = 8,
//                    title = "프로젝트 명",
//                    teamname = "제작연도",
//                    category = "미니 프로젝트",
//                    description =
//                        "도담도담은 외출/외박 신청, 심야 자습 신청, 급식 확인, 기숙사 아침 기상송 확인, 학교와 기숙사 상벌점 조회, 퇴사 버스 신청, 학교 일정 조회 기능을 제공합니다.",
//                )
//            )
//            else -> emptyList()
//        }
//        _archiveList.value = mockData
//    }
}