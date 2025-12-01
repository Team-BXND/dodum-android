package com.example.dodum_android.feature.misc.share

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dodum_android.network.misc.MiscItemList
import com.example.dodum_android.network.misc.MiscListData
import com.example.dodum_android.network.misc.MiscService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.min

@HiltViewModel
class MShareViewModel @Inject constructor(
    private val miscService: MiscService
) : ViewModel() {

    var miscList by mutableStateOf<List<MiscItemList>>(emptyList())
        private set

    var currentPage by mutableStateOf(1)
        private set

    var totalPages by mutableStateOf(1)
        private set

    private val pageSize = 7

    // 더미 데이터 (최신 글이 앞으로 오도록 정렬)
    private val fakeData = List(30) { index ->
        MiscItemList(
            id = index + 1L,
            title = "게시글 ${index + 1}",
            content = "내용 ${index + 1}",
            likes = (1..10).random(),
            category = "share",
            isApproved = true,
            author = "작성자 ${index + 1}"
        )
    }.sortedByDescending { it.id }


    init {
        updateTotalPages(fakeData.size)
        loadPage(1)
    }

    fun loadPage(page: Int) {
        currentPage = page
        miscList = getPageData(page)
    }

    private fun updateTotalPages(totalCount: Int) {
        totalPages = ceil(totalCount / pageSize.toFloat()).toInt()
    }

    private fun getPageData(page: Int): List<MiscItemList> {
        val from = (page - 1) * pageSize
        val to = min(from + pageSize, fakeData.size)
        return fakeData.subList(from, to)
    }
}
