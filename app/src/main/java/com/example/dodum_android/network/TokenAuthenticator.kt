package com.example.dodum_android.network

import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.network.start.signin.RefreshTokenRequest
import com.example.dodum_android.network.start.signin.SigninService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
    private val userRepository: UserRepository,
    private val signinService: Provider<SigninService>
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.responseCount > 3) return null

        val newToken = runBlocking {
            val username = userRepository.getPublicIdSnapshot()
            val refreshToken = userRepository.getRefreshTokenSnapshot()

            if (username != null && refreshToken != null) {
                try {
                    val refreshResponse = signinService.get().refreshToken(
                        RefreshTokenRequest(username, refreshToken)
                    )

                    if (refreshResponse.isSuccessful && refreshResponse.body()?.status == 200) {
                        val newAccessToken = refreshResponse.body()?.data?.accessToken
                        if (newAccessToken != null) {
                            userRepository.saveUserData(accessToken = newAccessToken)
                            newAccessToken
                        } else {
                            null
                        }
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }
        }

        return if (newToken != null) {
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        } else {
            null
        }
    }
}