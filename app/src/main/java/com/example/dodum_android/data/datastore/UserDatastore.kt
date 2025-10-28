package com.example.dodum_android.data.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserPrefsKeys {
    val PUBLIC_ID = stringPreferencesKey("public_id")
    val TOKEN = stringPreferencesKey("token")
}