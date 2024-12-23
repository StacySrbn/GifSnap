package com.example.gifsnap.data.local

import androidx.room.*

@Database(
    entities = [
        GifEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GifDatabase: RoomDatabase() {
    abstract val gifDao: GifDao
}