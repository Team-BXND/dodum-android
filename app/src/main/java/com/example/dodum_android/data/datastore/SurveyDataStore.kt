package com.example.dodum_android.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dodum_android.data.question.MixedQuestions
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.surveyDataStore by preferencesDataStore("survey_data")

class SurveyDataStore(private val context: Context) {

    private val KEY_SURVEY = stringPreferencesKey("survey_questions")

    suspend fun saveSurvey(questions: MixedQuestions) {
        val json = Json.encodeToString(questions)
        context.surveyDataStore.edit { it[KEY_SURVEY] = json }
    }

    suspend fun loadSurvey(): MixedQuestions? {
        val prefs = context.surveyDataStore.data.first()
        val json = prefs[KEY_SURVEY] ?: return null
        return Json.decodeFromString(json)
    }
}
