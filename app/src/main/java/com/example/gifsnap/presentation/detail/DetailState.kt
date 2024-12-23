package com.example.gifsnap.presentation.detail

import com.example.gifsnap.domain.models.Gif

data class DetailState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val gif: Gif = Gif("", "", "")
)
