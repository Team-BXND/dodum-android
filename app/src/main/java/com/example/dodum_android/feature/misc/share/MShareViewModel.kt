package com.example.dodum_android.feature.misc.share

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.network.misc.MiscCategory
import com.example.dodum_android.network.misc.MiscCriteria
import com.example.dodum_android.network.misc.MiscItemList
import com.example.dodum_android.network.misc.MiscService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class MShareViewModel @Inject constructor(
    private val miscService: MiscService
) : ViewModel() {


    /* ---------- State ---------- */

    private val _miscList = MutableStateFlow<List<MiscItemList>>(emptyList())
    val miscList: StateFlow<List<MiscItemList>> = _miscList.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _totalPages = MutableStateFlow(1)
    val totalPages: StateFlow<Int> = _totalPages.asStateFlow()

    private val pageSize = 7

    private var currentCategory: MiscCategory = MiscCategory.LECTURE_RECOMMENDATION
    private var currentCriteria: MiscCriteria = MiscCriteria.LATEST

    /* ---------- init ---------- */

    init {
        loadPage(1)
    }

    /* ---------- Page ---------- */

    fun loadPage(page: Int) {
        _currentPage.value = page

        viewModelScope.launch {
            runCatching {
                miscService.getMiscList(
                    category = currentCategory.id,
                    criteria = currentCriteria.name,
                    page = page
                )
            }.onSuccess { response ->
                if (response.success && response.data != null) {
                    _miscList.value = response.data.infos
                    updateTotalPages(response.data.infos.size)
                }
            }
        }
    }

    /* ---------- Category ---------- */

    fun changeCategory(category: MiscCategory) {
        currentCategory = category
        loadPage(1)
    }

    /* ---------- Criteria ---------- */

    fun changeCriteria(criteria: MiscCriteria) {
        currentCriteria = criteria
        loadPage(1)
    }

    /* ---------- Page Count ---------- */

    private fun updateTotalPages(totalCount: Int) {
        _totalPages.value = ceil(totalCount / pageSize.toFloat()).toInt()
    }
}