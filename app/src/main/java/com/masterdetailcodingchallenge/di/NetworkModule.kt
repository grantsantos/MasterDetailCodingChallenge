package com.masterdetailcodingchallenge.di

import com.masterdetailcodingchallenge.common.data.remote.api.ItunesSearchApi
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
    fun provideItunesSearchApi(): ItunesSearchApi {
        return Retrofit.Builder()
            .baseUrl(ItunesSearchApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItunesSearchApi::class.java)
    }
}