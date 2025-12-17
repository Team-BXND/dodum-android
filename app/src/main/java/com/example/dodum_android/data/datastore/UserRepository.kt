package com.example.dodum_android.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.dodum_android.data.datastore.UserPrefsKeys.ACCESS_TOKEN
import com.example.dodum_android.data.datastore.UserPrefsKeys.PUBLIC_ID
import com.example.dodum_android.data.datastore.UserPrefsKeys.REFRESH_TOKEN
import com.example.dodum_android.network.start.signin.RefreshTokenRequest
import com.example.dodum_android.network.start.signin.SigninService
import dagger.Lazy // ★ 수정 1: 이 import가 꼭 필요합니다.
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signinService: Lazy<SigninService> // dagger.Lazy를 사용
) {
    private var cachedAccessToken: String? = null
    private var cachedRefreshToken: String? = null

    suspend fun loadAccessToken() {
        cachedAccessToken = getAccessTokenSnapshot()
        cachedRefreshToken = getRefreshTokenSnapshot()
    }

    fun getCachedAccessToken(): String? = cachedAccessToken
    fun getCachedRefreshToken(): String? = cachedRefreshToken

    val publicIdFlow: Flow<String?> = context.dataStore.data
        .map { it[PUBLIC_ID] }

    suspend fun saveUserData(
        publicId: String? = null,
        accessToken: String? = null,
        refreshToken: String? = null
    ) {
        context.dataStore.edit { prefs ->
            publicId?.let { prefs[PUBLIC_ID] = it }
            accessToken?.let {
                prefs[ACCESS_TOKEN] = it
                cachedAccessToken = it
            }
            refreshToken?.let {
                prefs[REFRESH_TOKEN] = it
                cachedRefreshToken = it
            }
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { prefs ->
            prefs.remove(PUBLIC_ID)
            prefs.remove(ACCESS_TOKEN)
            prefs.remove(REFRESH_TOKEN)
        }
        cachedAccessToken = null
        cachedRefreshToken = null
    }

    suspend fun getPublicIdSnapshot(): String? =
        context.dataStore.data.map { it[PUBLIC_ID] }.first()

    suspend fun getAccessTokenSnapshot(): String? =
        context.dataStore.data.map { it[ACCESS_TOKEN] }.first()

    suspend fun getRefreshTokenSnapshot(): String? =
        context.dataStore.data.map { it[REFRESH_TOKEN] }.first()

    fun refreshToken(): String? = runBlocking {
        val refreshToken = getRefreshTokenSnapshot()
        val username = getPublicIdSnapshot()

        if (refreshToken != null && username != null) {
            try {
                // signinService.get() 호출 가능 (dagger.Lazy import 덕분)
                val response = signinService.get().refreshToken(
                    RefreshTokenRequest(username, refreshToken)
                )

                if (response.isSuccessful && response.body() != null) {
                    // ★ 수정 2: response.body() 안의 data 객체를 통해 accessToken에 접근
                    val newAccessToken = response.body()?.data?.accessToken

                    if (newAccessToken != null) {
                        // 새 토큰 저장
                        saveUserData(accessToken = newAccessToken)
                        return@runBlocking newAccessToken
                    } else {
                        clearUserData()
                    }
                } else {
                    // 갱신 실패 시 로그아웃 처리 등
                    clearUserData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                clearUserData()
            }
        }
        return@runBlocking null
    }
}