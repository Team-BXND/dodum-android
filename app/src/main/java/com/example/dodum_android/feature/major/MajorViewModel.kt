package com.example.dodum_android.feature.major

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dodum_android.data.datastore.SurveyDataStore
import com.example.dodum_android.data.question.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MajorViewModel @Inject constructor(
    private val surveyDataStore: SurveyDataStore
) : ViewModel() {

    private val _questions = MutableStateFlow<MixedQuestions?>(null)
    val questions = _questions.asStateFlow()

    private val objectiveAnswers = mutableMapOf<Int, Int>()
    private val subjectiveAnswers = mutableMapOf<String, String>()

    init {
        loadSurvey()
    }

    fun generateAndSave() {
        viewModelScope.launch {
            val mixed = generateMixedQuestions()
            surveyDataStore.saveSurvey(mixed)
            _questions.value = mixed
        }
    }

    fun loadSurvey() {
        viewModelScope.launch {
            _questions.value = surveyDataStore.loadSurvey()
        }
    }

    fun selectObjective(questionId: Int, choice: Int) {
        objectiveAnswers[questionId] = choice
    }

    fun inputSubjective(key: String, text: String) {
        subjectiveAnswers[key] = text
    }

    fun buildRequest(): SurveyAnswerRequest {

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
}
