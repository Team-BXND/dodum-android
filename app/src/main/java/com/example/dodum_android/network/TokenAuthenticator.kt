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

        val requestPath = response.request.url.encodedPath
        if (requestPath.startsWith("/auth")) {
            return null
        }

        // 재시도 3회 초과 시 중단
        if (response.retryCount() > 3) return null

        val newToken = runBlocking {
            val username = userRepository.getPublicIdSnapshot()
            val refreshToken = userRepository.getRefreshTokenSnapshot()

            if (username == null || refreshToken == null) return@runBlocking null

            try {
                val refreshResponse = signinService.get().refreshToken(
                    RefreshTokenRequest(username, refreshToken)
                )

                if (refreshResponse.isSuccessful && refreshResponse.body()?.status == 200) {
                    refreshResponse.body()?.data?.accessToken?.also { token ->
                        userRepository.saveUserData(accessToken = token)
                    }
                } else {
                    // [수정] 토큰 갱신 실패 시(401 등), 저장된 쓰레기 토큰을 지워야 함
                    userRepository.clearUserData()
                    null
                }
            } catch (e: Exception) {
                // [수정] 네트워크 오류 등으로 실패 시에도 안전하게 null 반환
                null
            }
        }

        return newToken?.let {
            response.request.newBuilder()
                .header("Authorization", "Bearer $it")
                .build()
        }
    }
}

private fun Response.retryCount(): Int {
    var count = 1
    var prior = priorResponse
    while (prior != null) {
        count++
        prior = prior.priorResponse
    }
    return count
}
