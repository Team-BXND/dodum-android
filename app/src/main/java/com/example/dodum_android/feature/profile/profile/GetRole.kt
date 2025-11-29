package com.example.dodum_android.feature.profile.profile

import android.util.Base64
import org.json.JSONObject

fun GetRole(token: String): String? {
    return try {
        val parts = token.split(".")
        if (parts.size != 3) return null

        val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
        val json = JSONObject(payload)
        json.getString("role")
    } catch (e: Exception) {
        null
    }
}
