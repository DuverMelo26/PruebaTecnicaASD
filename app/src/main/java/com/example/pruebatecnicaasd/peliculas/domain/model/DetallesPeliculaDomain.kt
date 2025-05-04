package com.example.pruebatecnicaasd.peliculas.domain.model

data class DetallesPeliculaDomain(
    val titulo: String,
    val urlCartel: String,
    val id: Int,
    val descripcion: String,
    val generos: List<String>,
    val calificacion: Float,
    val lema: String
)
