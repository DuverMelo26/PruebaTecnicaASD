package com.example.pruebatecnicaasd.peliculas.data.model

import com.google.gson.annotations.SerializedName

data class DetallesPeliculaDTO(
    @SerializedName("title") val titulo: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val descripcion: String?,
    @SerializedName("genres") val generos: List<GenerosPeliculaDTO>?,
    @SerializedName("vote_average") val calificacion: Float?,
    @SerializedName("tagline") val lema: String?
)
