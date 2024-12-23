package com.example.gifsnap.data.remote.response

data class GifListDto(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)