package com.example.gifsnap.data.remote

import com.example.gifsnap.data.remote.response.GifListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GifApi {

    @GET("gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("q") query: String = DEFAULT_QUERY,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("offset") offset: Int = 0,
        @Query("rating") rating: String = DEFAULT_RATING,
        @Query("lang") lang: String = "en"
    ): GifListDto

    companion object {
        const val BASE_URL = "https://api.giphy.com/v1/"
        const val API_KEY = "YGHnKKBGSydS6nSt6WAoUcICWwmgCfvL"
        const val DEFAULT_QUERY = "cats"
        const val DEFAULT_RATING = "g"
        const val DEFAULT_LIMIT = 25
    }

}