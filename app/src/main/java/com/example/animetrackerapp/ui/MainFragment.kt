package com.example.animetrackerapp.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animetrackerapp.R
import com.example.animetrackerapp.UiEvent
import com.example.animetrackerapp.databinding.FragmentMainBinding
import com.example.animetrackerapp.model.Anime
import com.example.animetrackerapp.model.AnimeRepository
import com.example.animetrackerapp.ui.adapter.AnimeListAdapter
import kotlinx.coroutines.launch

class MainActivity : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(AnimeRepository())
    }

    private val adapter = AnimeListAdapter { viewModel.onAnimeClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMainBinding.bind(view).apply {
            animeListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            animeListRecyclerView.adapter = adapter
        }

        binding.collectUiState()
        collectUiEvents()
    }

    private fun FragmentMainBinding.collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { updateUI(it) }
            }
        }
    }

    private fun collectUiEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { uiEvent ->
                    when (uiEvent) {
                        is UiEvent.NavigateTo -> navigateTo(uiEvent.anime)
                    }
                }
            }
        }
    }

    private fun FragmentMainBinding.updateUI(state: UiState) {
        progress.visibility = if (state.loading) VISIBLE else GONE
        state.anime?.data?.let(adapter::submitList)

        if (state.error != null) {
            Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateTo(anime: Anime) {
        val navAction = MainActivityDirections.actionMainToAnimeDetail(anime)
        findNavController().navigate(directions = navAction)
    }
}
