package com.example.gifsnap.domain.usecases

import androidx.paging.PagingData
import com.example.gifsnap.domain.models.Gif
import com.example.gifsnap.domain.repository.GifRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGifListUseCase @Inject constructor(
    private val gifRepository: GifRepository
) {
    suspend operator fun invoke(): Flow<PagingData<Gif>>{
        return gifRepository.getGifsList()
    }
}