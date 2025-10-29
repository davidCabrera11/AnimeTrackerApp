package com.example.animetrackerapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.animetrackerapp.UiEvent
import com.example.animetrackerapp.model.Anime
import com.example.animetrackerapp.model.AnimeRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val animeRepository: AnimeRepository) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _events = Channel<UiEvent>()
    val events = _events.receiveAsFlow()

    init {
        getTopAnime()
    }

    fun getTopAnime() {
        _state.value = UiState(loading = true)

        viewModelScope.launch {
            try {
                _state.value = UiState(loading = false)
                _state.value = UiState(anime = animeRepository.getTopAnime())
            } catch (e: Exception) {
                _state.value = UiState(loading = false)
                Log.e("API_ERROR", "Failed to fetch users", e)
            }
        }
    }

    fun onAnimeClicked(anime: Anime) {
        viewModelScope.launch {
            _events.send(UiEvent.NavigateTo(anime))
        }
    }
}

/*
 ViewModel factory is required when no DI is used
 and viewmodel has parameters
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val animeRepository: AnimeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(animeRepository) as T
    }
}