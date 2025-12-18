package com.example.dodum_android.network

import com.example.dodum_android.data.datastore.UserRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userRepository: UserRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = userRepository.getCachedAccessToken()

        val requestBuilder = originalRequest.newBuilder()

        // /auth/** 요청에는 Authorization 헤더를 붙이지 않음
        if (!originalRequest.url.encodedPath.startsWith("/auth")) {
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}