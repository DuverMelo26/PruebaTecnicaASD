package com.example.pruebatecnicaasd.peliculas.data.model

import com.google.gson.annotations.SerializedName

data class PeliculaPopularDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val titulo: String?,
    @SerializedName("overview") val descripcion: String?,
    @SerializedName("poster_path") val posterPath: String?
)
