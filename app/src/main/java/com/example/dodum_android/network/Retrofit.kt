package com.example.dodum_android.network

import com.example.dodum_android.data.datastore.UserRepository
import com.example.dodum_android.network.profile.myinfo.MyInfoService
import com.example.dodum_android.network.profile.mypost.MyPostService
import com.example.dodum_android.network.profile.password.PwService
import com.example.dodum_android.network.start.email.EmailService
import com.example.dodum_android.network.start.signin.SigninService
import com.example.dodum_android.network.start.signout.SignOutService
import com.example.dodum_android.network.start.signup.SignupService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(userRepository: UserRepository): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor { userRepository.getCachedAccessToken() })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DodumUrl.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideSigninService(retrofit: Retrofit): SigninService =
        retrofit.create(SigninService::class.java)

    @Provides
    @Singleton
    fun provideSignupService(retrofit: Retrofit): SignupService =
        retrofit.create(SignupService::class.java)


    @Provides
    @Singleton
    fun providesSignOutService(retrofit: Retrofit): SignOutService =
        retrofit.create(SignOutService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
object EmailModule {

    @Provides
    @Singleton
    fun provideEmailService(retrofit: Retrofit): EmailService =
        retrofit.create(EmailService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
object InfoModule {

    @Singleton
    @Provides
    fun provideInfoService(retrofit: Retrofit): MyInfoService =
        retrofit.create(MyInfoService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Singleton
    @Provides
    fun provideMypostService(retrofit: Retrofit): MyPostService =
        retrofit.create(MyPostService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
object PwModule {
    @Singleton
    @Provides
    fun providePwService(retrofit: Retrofit): PwService =
        retrofit.create(PwService::class.java)
}