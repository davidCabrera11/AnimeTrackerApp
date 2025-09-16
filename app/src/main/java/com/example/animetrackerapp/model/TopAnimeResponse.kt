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
    val images: Images,
    val score: Double,
    val year: Int?,
    val synopsis: String
) : Parcelable

@Parcelize
data class Images(
    val jpg: Jpg
) : Parcelable

@Parcelize
data class Jpg(
    @SerializedName("image_url")
    val imageUrl: String
) : Parcelable