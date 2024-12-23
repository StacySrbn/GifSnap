package com.example.gifsnap.domain.usecases

import android.util.Log
import com.example.gifsnap.util.Resource
import com.example.gifsnap.domain.models.Gif
import com.example.gifsnap.domain.repository.GifRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSingleGifUseCase @Inject constructor(
    private val gifRepository: GifRepository
) {
    suspend operator fun invoke (id: String): Flow<Resource<Gif>>{
        Log.d("Error check", "usecase $id")
        return gifRepository.getSingleGif(id)
    }
}