package com.example.dodum_android.data.question

fun generateMixedQuestions(): MixedQuestions {

    val objectiveList = QuestionLocalData.QUESTION_MAP.entries
        .map { ObjectiveQuestion(it.key, it.value) }
        .shuffled()
        .take(18)

    val subjectiveList = QuestionLocalData.SUBJECTIVE_MAP.entries
        .map { SubjectiveQuestion(it.key, it.value) }
        .shuffled()
        .take(2)

    return MixedQuestions(
        subjective = subjectiveList,
        objective = objectiveList
    )
}