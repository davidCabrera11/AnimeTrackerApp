package com.example.animetrackerapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animetrackerapp.model.Anime

class AnimeDetailViewModel(anime: Anime) : ViewModel() {

    class UiState(val anime: Anime)

    private val _state = MutableLiveData(UiState(anime))
    val state: LiveData<UiState> get() = _state

}

@Suppress("UNCHECKED_CAST")
class AnimeDetailViewModelFactory(private val anime: Anime) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimeDetailViewModel(anime) as T
    }
}