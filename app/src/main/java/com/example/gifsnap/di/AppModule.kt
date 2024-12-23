package com.example.gifsnap.di

import android.app.Application
import androidx.room.Room
import com.example.gifsnap.data.local.GifDatabase
import com.example.gifsnap.data.remote.GifApi
import com.example.gifsnap.domain.usecases.GetGifListUseCase
import com.example.gifsnap.domain.repository.GifRepository
import com.example.gifsnap.domain.usecases.GetSingleGifUseCase
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesGifApi(): GifApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GifApi.BASE_URL)
            .client(client)
            .build()
            .create(GifApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): GifDatabase {
        return Room.databaseBuilder(
            app,
            GifDatabase::class.java,
            "gifs_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesGetGifListUseCase(
        gifRepository: GifRepository
    ) : GetGifListUseCase = GetGifListUseCase(gifRepository)

    @Provides
    @Singleton
    fun provideGetSingleGifUseCase(
        gifRepository: GifRepository
    ) :  GetSingleGifUseCase = GetSingleGifUseCase(gifRepository)


}