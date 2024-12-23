package com.example.gifsnap.mappers

import com.example.gifsnap.data.local.GifEntity
import com.example.gifsnap.data.remote.response.Data
import com.example.gifsnap.domain.models.Gif

fun Data.toGifEntity(): GifEntity {
    return GifEntity(
        id = this.id,
        title = this.title,
        url = this.images.original.url
    )
}

fun GifEntity.toGif(): Gif {
    return Gif(
        id = this.id,
        title = this.title,
        url = this.url,
    )
}