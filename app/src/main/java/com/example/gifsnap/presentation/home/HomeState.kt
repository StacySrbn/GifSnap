package com.example.gifsnap.presentation.home

import androidx.paging.PagingData
import com.example.gifsnap.domain.models.Gif
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val gifsList: Flow<PagingData<Gif>> = emptyFlow()
)
