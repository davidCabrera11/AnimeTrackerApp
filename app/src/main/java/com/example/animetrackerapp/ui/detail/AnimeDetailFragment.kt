package com.example.animetrackerapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.animetrackerapp.R
import com.example.animetrackerapp.databinding.FragmentAnimeDetailBinding
import kotlinx.coroutines.launch

class AnimeDetailFragment : Fragment(R.layout.fragment_anime_detail) {

    private val safeArgs: AnimeDetailFragmentArgs by navArgs()

    private val viewModel: AnimeDetailViewModel by viewModels {
        AnimeDetailViewModelFactory(requireNotNull(safeArgs.anime))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAnimeDetailBinding.bind(view)

        binding.animeDetailToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { binding.updateUI(it) }
            }
        }
    }

    private fun FragmentAnimeDetailBinding.updateUI(state: AnimeDetailViewModel.UiState) {
        val anime = state.anime

        Glide.with(animeDetailImage.context)
            .load(anime.images.jpg.imageUrl)
            .into(animeDetailImage)

        animeDetailToolbar.title = anime.title
        animeSynopsis.text = anime.synopsis
    }
}
