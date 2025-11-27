package com.example.animetrackerapp

import android.app.Application
import androidx.room.Room
import com.example.animetrackerapp.model.database.AnimeDatabase

class App : Application() {

    lateinit var database: AnimeDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            context = this,
            AnimeDatabase::class.java,
            "anime_database"
        ).build()
    }
}
