package com.example.animetrackerapp

import com.example.animetrackerapp.model.Anime

sealed interface UiEvent {
    data class NavigateTo(val anime: Anime) : UiEvent
}