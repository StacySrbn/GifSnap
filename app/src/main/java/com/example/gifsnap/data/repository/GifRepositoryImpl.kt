package com.example.gifsnap.data.repository

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.paging.*
import androidx.paging.map
import com.example.gifsnap.util.Resource
import com.example.gifsnap.data.local.*
import com.example.gifsnap.data.remote.GifApi
import com.example.gifsnap.domain.models.Gif
import com.example.gifsnap.domain.repository.GifRepository
import com.example.gifsnap.mappers.toGif
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GifRepositoryImpl @Inject constructor(
    private val db: GifDatabase,
    private val api: GifApi
): GifRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getGifsList(): Flow<PagingData<Gif>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = GifRemoteMediator(gifDb = db, gifApi = api),
            pagingSourceFactory = { db.gifDao.getGifList() }
        ).flow.map { pagingData ->
            pagingData.map { gifEntity ->
                Log.d("MY LOG GifRepositoryImpl", "Mapping GifEntity to Gif: ${gifEntity.id}")
                gifEntity.toGif()
            }
        }
    }

    override suspend fun getSingleGif(id: String): Flow<Resource<Gif>>{
        return flow {
            emit(Resource.Loading(true))
            try {
                val gifEntity = db.gifDao.getGifById(id)
                Log.d("Error Check", "Repository entity $gifEntity")

                if (gifEntity != null) {
                    emit(Resource.Success(gifEntity.toGif()))
                } else {
                    emit(Resource.Error("GIF not found"))
                }
            } catch (e: IllegalStateException) {
                emit(Resource.Error("Database is in an illegal state: ${e.localizedMessage}"))
            } catch (e: SQLiteException) {
                emit(Resource.Error("SQLite exception: ${e.localizedMessage}"))
            } catch (e: Exception) {
                emit(Resource.Error("Unexpected error: ${e.localizedMessage}"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }
}
