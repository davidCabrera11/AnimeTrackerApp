package com.example.animetrackerapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animetrackerapp.R
import com.example.animetrackerapp.databinding.ItemAnimeBinding
import com.example.animetrackerapp.model.Anime

class AnimeAdapter(
    private var animeList: List<Anime>,
    private val onItemClick: (Anime) -> Unit
) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animeList[position]
        holder.bind(anime)
        holder.itemView.setOnClickListener {
            onItemClick(anime)
        }
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    fun updateData(newList: List<Anime>?) {
        if (newList != null) {
            animeList = newList
        }
        notifyDataSetChanged()
    }

    class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemAnimeBinding.bind(view)

        fun bind(anime: Anime) {
            Glide.with(binding.animeImage.context)
                .load(anime.images.jpg.imageUrl)
                .into(binding.animeImage)

            binding.animeTitle.text = anime.title
            binding.animeYear.text = anime.year.toString()
            binding.animeScore.text = itemView.context.getString(R.string.anime_score, anime.score)
        }
    }
}