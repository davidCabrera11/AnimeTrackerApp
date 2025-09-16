package com.example.animetrackerapp.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.animetrackerapp.R
import com.example.animetrackerapp.databinding.ActivityAnimeDetailBinding
import com.example.animetrackerapp.model.Anime


class AnimeDetailActivity : AppCompatActivity() {

    private val viewModel: AnimeDetailViewModel by viewModels {
        AnimeDetailViewModelFactory(requireNotNull(intent.getParcelableExtra(ANIME)))
    }

    private lateinit var binding: ActivityAnimeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAnimeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyWindowInsets()

        viewModel.state.observe(this) {
            updateUI(it.anime)
        }
    }

    private fun updateUI(anime: Anime) {
        Glide.with(binding.animeDetailImage.context)
            .load(anime.images.jpg.imageUrl)
            .into(binding.animeDetailImage)

        binding.animeDetailToolbar.title = anime.title
        binding.animeSynopsis.text = anime.synopsis
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.anime_detail_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        const val ANIME = "AnimeDetailActivity:anime"
    }
}