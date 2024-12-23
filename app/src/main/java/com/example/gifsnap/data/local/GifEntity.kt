package com.example.gifsnap.data.local

import androidx.room.*

@Entity(tableName = "gifs")
data class GifEntity(
    @PrimaryKey
    val id: String,

    val title: String,
    val url: String
)
