package com.example.gifsnap.data.local

import android.util.Log
import androidx.paging.*
import com.example.gifsnap.data.remote.GifApi
import com.example.gifsnap.mappers.toGifEntity
import okio.IOException
import retrofit2.HttpException
import androidx.room.withTransaction
import com.example.gifsnap.data.remote.GifApi.Companion.DEFAULT_LIMIT

@OptIn(ExperimentalPagingApi::class)
class GifRemoteMediator(
    private val gifDb: GifDatabase,
    private val gifApi: GifApi,
) : RemoteMediator<Int, GifEntity>() {

    private var pagination = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GifEntity>
    ): MediatorResult {

        return try {
            Log.d("MY LOG GifRemoteMediator", "LoadType: $loadType")
            Log.d("MY LOG GifRemoteMediator", "Pagination info: $pagination")
            val pageOffset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    pagination++
                }
            }
            Log.d("MY LOG GifRemoteMediator", "Loading page: $pageOffset")

            val responseGifList = gifApi.searchGifs(
                offset = pageOffset
            )

            val gifEntities = responseGifList.data.map { gifDto ->
                gifDto.toGifEntity().also {
                    Log.d("MY LOG GifRemoteMediator", "Converted GifDto to GifEntity: ${it.id}")
                }
            }

            gifDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    Log.d("MY LOG GifRemoteMediator", "Clearing old data in the database")
                    gifDb.gifDao.clearAllGifs()
                }
                Log.d("MY LOG GifRemoteMediator", "Inserting new data into the database")
                gifDb.gifDao.upsertGifList(gifEntities)
            }

            val endOfPaginationReached = pageOffset >= gifApi.searchGifs().pagination.total_count
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            Log.e("MY LOG GifRemoteMediator", "IOException: ${e.message}")
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("MY LOG GifRemoteMediator", "HttpException: ${e.code()} - ${e.message()}")
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.e("MY LOG GifRemoteMediator", "Exception: ${e.message}")
            MediatorResult.Error(e)
        }
    }
}
