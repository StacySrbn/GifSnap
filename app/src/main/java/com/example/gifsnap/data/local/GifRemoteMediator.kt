package com.example.gifsnap.data.local

import android.util.Log
import androidx.paging.*
import com.example.gifsnap.data.remote.GifApi
import com.example.gifsnap.mappers.toGifEntity
import okio.IOException
import retrofit2.HttpException
import androidx.room.withTransaction
import com.example.gifsnap.data.remote.GifApi.Companion.DEFAULT_LIMIT
import com.example.gifsnap.data.remote.GifApi.Companion.DEFAULT_OFFSET

@OptIn(ExperimentalPagingApi::class)
class GifRemoteMediator(
    private val gifDb: GifDatabase,
    private val gifApi: GifApi,
) : RemoteMediator<Int, GifEntity>() {

    private var currentOffset = DEFAULT_OFFSET
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GifEntity>
    ): MediatorResult {

        return try {
            Log.d("MY LOG GifRemoteMediator", "LoadType: $loadType")
            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    currentOffset += DEFAULT_LIMIT
                    currentOffset
                }
            }
            Log.d("MY LOG GifRemoteMediator", "Loading page: $offset")

            val responseGifList = gifApi.searchGifs(offset = offset)

            val gifEntities = responseGifList.data.map { gifDto ->
                gifDto.toGifEntity()
            }

            gifDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    gifDb.gifDao.clearAllGifs()
                }
                gifDb.gifDao.upsertGifList(gifEntities)
            }
            val endOfPaginationReached = offset >= gifApi.searchGifs().pagination.total_count
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
