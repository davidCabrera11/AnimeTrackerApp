package com.example.animetrackerapp.ui

import com.example.animetrackerapp.model.Anime

sealed interface UiEvent {
    data class NavigateTo(val anime: Anime) : UiEvent
}