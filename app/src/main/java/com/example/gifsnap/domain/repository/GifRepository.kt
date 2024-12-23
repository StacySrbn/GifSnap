package com.example.gifsnap.domain.repository

import androidx.paging.PagingData
import com.example.gifsnap.util.Resource
import com.example.gifsnap.domain.models.Gif
import kotlinx.coroutines.flow.Flow

interface GifRepository {
    suspend fun getGifsList(): Flow<PagingData<Gif>>
    suspend fun getSingleGif(id: String): Flow<Resource<Gif>>
}