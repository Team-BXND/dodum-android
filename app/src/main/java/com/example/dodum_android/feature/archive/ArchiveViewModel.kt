package com.example.dodum_android.feature.archive

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.archive.ArchiveItem
import com.example.dodum_android.network.archive.ArchiveService
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
            }
        }
    }

    //더미 데이터 생성
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