package com.example.pruebatecnicaasd.peliculas.data.model

import com.google.gson.annotations.SerializedName

data class GenerosPeliculaDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val nombre: String,
)
