package com.example.animetrackerapp.ui

import com.example.animetrackerapp.model.TopAnimeResponse

data class UiState(
    val loading: Boolean = false,
    val anime: TopAnimeResponse? = null,
    val error: String? = null,
)
