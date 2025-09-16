package com.example.animetrackerapp.model

class AnimeRepository {

    suspend fun getTopAnime() = RemoteConnection.service.getTopAnime()
}