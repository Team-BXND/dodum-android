package com.example.dodum_android.feature.major.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.data.datastore.SurveyDataStore
import com.example.dodum_android.data.question.MixedQuestions
import com.example.dodum_android.data.question.QuestionLocalData
import com.example.dodum_android.data.question.SurveyAnswerRequest
import com.example.dodum_android.data.question.generateMixedQuestions
import com.example.dodum_android.network.major.MajorRecommendResponse
import com.example.dodum_android.network.major.MajorService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MajorViewModel @Inject constructor(
    private val surveyDataStore: SurveyDataStore,
    private val majorService: MajorService
) : ViewModel() {

    private val _questions = MutableStateFlow<MixedQuestions?>(null)
    val questions = _questions.asStateFlow()

    private val _result = MutableStateFlow<MajorRecommendResponse?>(null)
    val result = _result.asStateFlow()

    private val objectiveAnswers = mutableMapOf<Int, Int>()
    private val subjectiveAnswers = mutableMapOf<String, String>()

    init {
        loadSurvey()
    }

    fun loadSurvey() {
        viewModelScope.launch {
            _result.value = null
            _questions.value = surveyDataStore.loadSurvey()
        }
    }

    fun selectObjective(questionId: Int, choice: Int) {
        objectiveAnswers[questionId] = choice
    }

    fun inputSubjective(key: String, text: String) {
        subjectiveAnswers[key] = text
    }

    fun requestMajorRecommend() {
        viewModelScope.launch {
            try {
                val request = buildRequest()
                val response = majorService.recommendMajor(request)
                _result.value = response

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun buildRequest(): SurveyAnswerRequest {
        val objectPart = objectiveAnswers.map {
            it.key.toString() to it.value.toString()
        }.toMap()

        val subjectPart = subjectiveAnswers.map { (key, text) ->
            val mappedKey = QuestionLocalData.SUBJECTIVE_KEY_MAP[key]
                ?: error("Invalid subjective key: $key")
            mappedKey.toString() to text
        }.toMap()

        return SurveyAnswerRequest(
            `object` = objectPart,
            subject = subjectPart
        )
    }

    fun generateAndSave() {
        viewModelScope.launch {
            val mixed = generateMixedQuestions()  // 기존 유틸 함수 사용
            surveyDataStore.saveSurvey(mixed)    // 로컬 DataStore에 저장
            _questions.value = mixed              // 화면에 표시
        }
    }
}