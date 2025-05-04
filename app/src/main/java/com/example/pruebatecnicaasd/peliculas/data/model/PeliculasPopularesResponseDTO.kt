package com.example.pruebatecnicaasd.peliculas.data.model

import com.google.gson.annotations.SerializedName

data class PeliculasPopularesResponseDTO(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val peliculas: List<PeliculaPopularDTO>
)
