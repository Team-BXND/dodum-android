package com.example.dodum_android.feature.info.share

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.info.InfoItem
import com.example.dodum_android.network.info.InfoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.min

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val infoService: InfoService
) : ViewModel() {

    private var allInfoList: List<InfoItem> = emptyList()

    var infoList by mutableStateOf<List<InfoItem>>(emptyList())
        private set

    var currentPage by mutableStateOf(1)
        private set

    var totalPages by mutableStateOf(1)
        private set

    private val pageSize = 10

    init {
        loadAllInfo()
    }

    /** 전체 게시글 조회 */
    private fun loadAllInfo() {
        viewModelScope.launch {
            runCatching {
                infoService.getInfoList(page = 1)
            }.onSuccess { response ->
                allInfoList = response.data

                totalPages = ceil(
                    allInfoList.size / pageSize.toFloat()
                ).toInt()

                loadPage(1)
            }
        }
    }

    /** 페이지 변경 */
    fun loadPage(page: Int) {
        currentPage = page

        val from = (page - 1) * pageSize
        val to = min(from + pageSize, allInfoList.size)

        infoList = if (from < allInfoList.size) {
            allInfoList.subList(from, to)
        } else {
            emptyList()
        }
    }
}