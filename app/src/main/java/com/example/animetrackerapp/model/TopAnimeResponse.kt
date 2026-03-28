package com.example.animetrackerapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TopAnimeResponse(
    val data: List<Anime>
)

@Parcelize
data class Anime(
    @SerializedName("mal_id")
    val malId: Int,
    val title: String,
    val type: String,
    val episodes: Int,
    val status: String,
    val aired: Aired,
    val rating: String,
    val images: Images,
    val score: Double,
    val rank: Int,
    val popularity: Int,
    val studios: List<Studios>,
    val synopsis: String
) : Parcelable

@Parcelize
data class Aired(
    @SerializedName("string")
    val datesAired: String
) : Parcelable

@Parcelize
data class Images(
    val jpg: Jpg
) : Parcelable

@Parcelize
data class Jpg(
    @SerializedName("large_image_url")
    val imageUrl: String
) : Parcelable

@Parcelize
data class Studios(
    val name: String
) : Parcelable