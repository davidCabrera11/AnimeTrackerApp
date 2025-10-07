package com.example.animetrackerapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animetrackerapp.model.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimeDetailViewModel(anime: Anime) : ViewModel() {

    class UiState(val anime: Anime)

    private val _state = MutableStateFlow(UiState(anime))
    val state: StateFlow<UiState> get() = _state.asStateFlow()

}

@Suppress("UNCHECKED_CAST")
class AnimeDetailViewModelFactory(private val anime: Anime) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimeDetailViewModel(anime) as T
    }
}