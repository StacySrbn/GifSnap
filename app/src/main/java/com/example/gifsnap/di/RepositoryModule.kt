package com.example.gifsnap.di

import com.example.gifsnap.data.repository.GifRepositoryImpl
import com.example.gifsnap.domain.repository.GifRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        gifRepositoryImpl: GifRepositoryImpl
    ): GifRepository
}
