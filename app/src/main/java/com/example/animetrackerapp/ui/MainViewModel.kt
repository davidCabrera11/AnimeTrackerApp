package com.example.animetrackerapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.animetrackerapp.model.AnimeRepository
import kotlinx.coroutines.launch

class MainViewModel(private val animeRepository: AnimeRepository) : ViewModel() {

    private val _state = MutableLiveData<UiState>()
    val state: LiveData<UiState> = _state

    init {
        getTopAnime()
    }

    fun getTopAnime() {
        Log.d("VIEWMODEL", "getTopAnime() called")

        _state.value = UiState(loading = true)

        viewModelScope.launch {
            try {
                val result = animeRepository.getTopAnime()
                _state.value = UiState(loading = false)
                _state.value = UiState(anime = result)
            } catch (e: Exception) {
                _state.value = UiState(loading = false)
                Log.e("API_ERROR", "Failed to fetch users", e)
            }
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