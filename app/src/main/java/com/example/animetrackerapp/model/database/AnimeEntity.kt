package com.example.animetrackerapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimeEntity(
    @PrimaryKey(autoGenerate = true)
    val malId: Int,
    val title: String,
    val score: Double,
    val year: Int?,
    val synopsis: String
)
