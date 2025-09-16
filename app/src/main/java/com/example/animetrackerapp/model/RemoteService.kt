package com.example.animetrackerapp.model

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): TopAnimeResponse
}