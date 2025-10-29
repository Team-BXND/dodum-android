package com.example.dodum_android.network

import com.example.dodum_android.network.start.email.EmailService
import com.example.dodum_android.network.start.signin.SigninService
import com.example.dodum_android.network.start.signup.SignupService
import com.example.dodum_android.remote.DodumUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DodumUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSigninService(retrofit: Retrofit): SigninService {
        return retrofit.create(SigninService::class.java)
    }

    @Provides
    @Singleton
    fun provideSignupService(retrofit: Retrofit): SignupService {
        return retrofit.create(SignupService::class.java)
    }

    @Provides
    @Singleton
    fun provideEmailService(retrofit: Retrofit): EmailService {
        return retrofit.create(EmailService::class.java)
    }

}