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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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

        Glide.with(imgAnimeDetail.context)
            .load(anime.images.jpg.imageUrl)
            .transform(RoundedCorners(24))
            .into(imgAnimeDetail)

        tvAnimeDetailTitle.text = anime.title
        tvRankValue.text = String.format("#%d", anime.rank)
        tvScoreValue.text = anime.score.toString()
        tvPopularityValue.text = String.format("#%d", anime.popularity)
        cardAnimeDetailInfo.tvStudioValue.text = anime.studios.firstOrNull()?.name ?: "Unknown"
        cardAnimeDetailInfo.tvEpisodesValue.text = anime.episodes.toString()
        cardAnimeDetailInfo.tvRatingValue.text = anime.rating
        cardAnimeDetailInfo.tvStatusValue.text = anime.status
        cardAnimeDetailInfo.tvAiredValue.text = anime.aired.datesAired
        tvAnimeDetailSynopsisContent.text = anime.synopsis
    }
}
