package com.example.animetrackerapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.animetrackerapp.model.AnimeRepository
import com.example.animetrackerapp.model.TopAnimeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val animeRepository: AnimeRepository) : ViewModel() {

    private val _anime = MutableLiveData<TopAnimeResponse>()
    val anime: LiveData<TopAnimeResponse> = _anime

    fun getTopAnime() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = animeRepository.getTopAnime()
                _anime.postValue(result)

            } catch (e: Exception) {
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