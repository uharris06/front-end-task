package com.uharris.frontendtask.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.uharris.frontendtask.data.OffersRepositoryImpl
import com.uharris.frontendtask.data.preferences.OffersPreferences
import com.uharris.frontendtask.data.services.OffersService
import com.uharris.frontendtask.data.source.OffersLocalDataSource
import com.uharris.frontendtask.data.source.OffersLocalDataSourceImpl
import com.uharris.frontendtask.data.source.OffersRemoteDataSource
import com.uharris.frontendtask.data.source.OffersRemoteDataSourceImpl
import com.uharris.frontendtask.utils.AppExecutor
import com.uharris.frontendtask.utils.Executor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun providesHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun providesRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://us-central1-techtaskapi.cloudfunctions.net/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun providesOffersService(retrofit: Retrofit): OffersService {
        return retrofit.create(OffersService::class.java)
    }

    @Provides
    @Singleton
    fun providesOffersPreferences(context: Context): OffersPreferences {
        return OffersPreferences(context)
    }

    @Provides
    @Singleton
    fun providesExecutor(): Executor {
        return AppExecutor()
    }

    @Provides
    @Singleton
    fun providesOffersRemoteDataSource(service: OffersService, executor: Executor): OffersRemoteDataSource {
        return OffersRemoteDataSourceImpl(service, executor)
    }

    @Provides
    @Singleton
    fun providesOffersLocalDataSource(offersPreferences: OffersPreferences): OffersLocalDataSource {
        return OffersLocalDataSourceImpl(offersPreferences)
    }
}