package com.example.animetrackerapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animetrackerapp.R
import com.example.animetrackerapp.databinding.FragmentMainBinding
import com.example.animetrackerapp.model.Anime
import com.example.animetrackerapp.model.AnimeRepository
import com.example.animetrackerapp.ui.adapter.AnimeAdapter
import kotlinx.coroutines.launch

class MainActivity : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(AnimeRepository())
    }

    private lateinit var adapter: AnimeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)

        binding.initRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { binding.updateUI(it) }
            }
        }

    }

    private fun FragmentMainBinding.updateUI(state: UiState) {
        progress.visibility = if (state.loading) View.VISIBLE else View.GONE

        if (state.error != null) {
            Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
        }

        state.anime.let { animeResponse ->
            adapter.updateData(animeResponse?.data)
        }
    }

    private fun FragmentMainBinding.initRecyclerView() {
        adapter = AnimeAdapter(emptyList()) { anime ->
            onItemSelected(anime)
        }
        animeListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        animeListRecyclerView.adapter = adapter
    }

    private fun onItemSelected(anime: Anime) {
        val navAction = MainActivityDirections.actionMainToAnimeDetail(anime)
        findNavController().navigate(directions = navAction)
    }
}