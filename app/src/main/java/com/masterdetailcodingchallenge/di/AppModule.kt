package com.masterdetailcodingchallenge.di

import com.masterdetailcodingchallenge.common.data.local.database.AppDatabase
import com.masterdetailcodingchallenge.common.data.remote.api.ItunesSearchApi
import com.masterdetailcodingchallenge.feature_itunes_search.data.repository.ItunesSearchRepositoryImpl
import com.masterdetailcodingchallenge.feature_itunes_search.domain.repository.ItunesSearchRepository
import com.masterdetailcodingchallenge.feature_itunes_search.domain.use_case.AllUseCases
import com.masterdetailcodingchallenge.feature_itunes_search.domain.use_case.ItunesSearchUseCase
import com.masterdetailcodingchallenge.feature_itunes_search.domain.use_case.UpdateFavoriteStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideItunesSearchRepository(
        api: ItunesSearchApi,
        db: AppDatabase
    ) : ItunesSearchRepository {
        return ItunesSearchRepositoryImpl(
            api = api,
            db = db
        )
    }

    @Provides
    @Singleton
    fun provideUseCases(
        repository: ItunesSearchRepository
    ) : AllUseCases {
        return AllUseCases(
            itunesSearchUseCase = ItunesSearchUseCase(repository),
            updateFavoriteStatusUseCase = UpdateFavoriteStatusUseCase(repository)
        )
    }
}