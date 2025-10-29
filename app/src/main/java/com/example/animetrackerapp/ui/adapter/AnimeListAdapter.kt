package com.example.animetrackerapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animetrackerapp.R
import com.example.animetrackerapp.databinding.ItemAnimeBinding
import com.example.animetrackerapp.model.Anime

class AnimeListAdapter(
    private val listener: (Anime) -> Unit
) : ListAdapter<Anime, AnimeListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anime = getItem(position)
        holder.bind(anime)
        holder.itemView.setOnClickListener { listener(anime) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemAnimeBinding.bind(view)

        fun bind(anime: Anime) = with(binding) {
            Glide.with(binding.animeImage.context)
                .load(anime.images.jpg.imageUrl)
                .into(binding.animeImage)

            animeTitle.text = anime.title
            animeYear.text = anime.year.toString()
            animeScore.text = itemView.context.getString(R.string.anime_score, anime.score)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Anime>() {
    override fun areItemsTheSame(oldItem: Anime, newItem: Anime) = oldItem.malId == newItem.malId
    override fun areContentsTheSame(oldItem: Anime, newItem: Anime) = oldItem == newItem
}