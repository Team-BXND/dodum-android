package com.example.dodum_android.data.question

import kotlinx.serialization.Serializable

@Serializable
data class ObjectiveQuestion(
    val id: Int,
    val text: String
)

@Serializable
data class SubjectiveQuestion(
    val key: String,
    val text: String
)

@Serializable
data class MixedQuestions(
    val subjective: List<SubjectiveQuestion>,
    val objective: List<ObjectiveQuestion>
)

@Serializable
data class SurveyAnswerRequest(
    val `object`: Map<String, String>,
    val subject: Map<String, String>
)
