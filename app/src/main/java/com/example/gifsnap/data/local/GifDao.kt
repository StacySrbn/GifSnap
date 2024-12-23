package com.example.gifsnap.data.local

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface GifDao {
    @Upsert
    suspend fun upsertGifList(movieList: List<GifEntity>)

    @Query("SELECT * FROM gifs WHERE id = :id LIMIT 1")
    suspend fun getGifById(id: String): GifEntity?

    @Query("SELECT * FROM gifs")
    fun getGifList(): PagingSource<Int, GifEntity>

    @Query("DELETE FROM gifs")
    suspend fun clearAllGifs()

}