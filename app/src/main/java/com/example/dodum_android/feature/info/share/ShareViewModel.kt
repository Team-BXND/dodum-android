package com.example.dodum_android.feature.info.share

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dodum_android.network.info.InfoListData
import com.example.dodum_android.network.info.InfoService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.min

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val infoService: InfoService
) : ViewModel() {

    var infoList by mutableStateOf<List<InfoListData>>(emptyList())
        private set

    var currentPage by mutableStateOf(1)
        private set

    var totalPages by mutableStateOf(1)
        private set

    private val pageSize = 7

    // 더미 데이터 (최신 글이 앞으로 오도록 정렬)
    private val fakeData = List(30) { index ->
        InfoListData(
            id = index + 1,
            title = "게시글 ${index + 1}",
            author = "작성자 ${index + 1}",
            likes = (1..10).random(),
            view = (50..200).random(),
            comment = (0..5).random(),
            image = null.toString()
        )
    }.sortedByDescending { it.id }   // 🔥 최신순

    init {
        updateTotalPages(fakeData.size)
        loadPage(1)
    }

    fun loadPage(page: Int) {
        currentPage = page
        infoList = getPageData(page)
    }

    private fun updateTotalPages(totalCount: Int) {
        totalPages = ceil(totalCount / pageSize.toFloat()).toInt()
    }

    private fun getPageData(page: Int): List<InfoListData> {
        val from = (page - 1) * pageSize
        val to = min(from + pageSize, fakeData.size)
        return fakeData.subList(from, to)
    }
}
