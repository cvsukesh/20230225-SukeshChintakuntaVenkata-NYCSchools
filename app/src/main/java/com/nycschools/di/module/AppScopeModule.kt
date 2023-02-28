package com.nycschools.di.module

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nycschools.data.network.util.AuthenticationInterceptor
import com.nycschools.data.network.NycApiService
import com.nycschools.mvvm.repo.NycRepository
import com.nycschools.mvvm.repo.NycRepositoryImpl
import com.nycschools.room.NycSchoolDAO
import com.nycschools.room.NycSchoolDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppScopeModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()


    @Singleton
    @Provides
    fun provideAuthenticationInterceptor(): AuthenticationInterceptor = AuthenticationInterceptor()

    @Singleton
    @Provides
    fun provideHttpClient(
        auth: AuthenticationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(auth)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideCommonService(retrofit: Retrofit): NycApiService =
        retrofit.create(NycApiService::class.java)

    @Provides
    fun provideNyCSchoolRepository(
        @ApplicationContext context: Context,
        serviceApi: NycApiService,
        nycDao: NycSchoolDAO
    ): NycRepository =
        NycRepositoryImpl(context, serviceApi, nycDao)

    @Provides
    fun provideNycSchoolDao(nycSchoolDatabase: NycSchoolDataBase): NycSchoolDAO =
        nycSchoolDatabase.getNycSchoolDao()

    @Provides
    fun provideNycSchoolDatabase(@ApplicationContext context: Context): NycSchoolDataBase =
        Room.databaseBuilder(context, NycSchoolDataBase::class.java, "nyc-high-school-database")
            .allowMainThreadQueries()
            .build()

    companion object {
        const val BASE_URL = "https://data.cityofnewyork.us/"
    }
}