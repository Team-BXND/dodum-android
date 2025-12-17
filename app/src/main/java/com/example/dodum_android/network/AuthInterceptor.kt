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
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}