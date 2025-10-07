package com.example.animetrackerapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animetrackerapp.R
import com.example.animetrackerapp.databinding.ActivityMainBinding
import com.example.animetrackerapp.model.Anime
import com.example.animetrackerapp.model.AnimeRepository
import com.example.animetrackerapp.ui.detail.AnimeDetailActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(AnimeRepository())
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AnimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyWindowInsets()

        initRecyclerView()

        viewModel.state.observe(this) { state ->
            updateUI(state)
        }
    }

    private fun updateUI(state: UiState) {
        binding.progress.visibility = if (state.loading) VISIBLE else GONE

        if (state.error != null) {
            Toast.makeText(this, state.error, Toast.LENGTH_SHORT).show()
        }

        state.anime.let { animeResponse ->
            adapter.updateData(animeResponse?.data)
        }
    }

    private fun initRecyclerView() {
        adapter = AnimeAdapter(emptyList()) { anime ->
            onItemSelected(anime)
        }
        binding.animeListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.animeListRecyclerView.adapter = adapter
    }

    private fun onItemSelected(anime: Anime) {
        val intent = Intent(this, AnimeDetailActivity::class.java)
        intent.putExtra(AnimeDetailActivity.ANIME, anime)
        startActivity(intent)
    }

    private fun applyWindowInsets() {
        setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}