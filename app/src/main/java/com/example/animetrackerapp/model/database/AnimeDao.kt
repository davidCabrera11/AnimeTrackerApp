package com.example.animetrackerapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Query("SELECT * FROM AnimeEntity")
    fun getAllAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM AnimeEntity WHERE malId = :id")
    fun findAnimeById(id: Int): Flow<AnimeEntity>

    @Query("SELECT COUNT(malId) FROM AnimeEntity")
    fun animeCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnime(vararg anime: AnimeEntity)

    @Update
    fun updateAnime(animeEntity: AnimeEntity)
}
