package com.example.pruebatecnicaasd.peliculas.data.mapper

import com.example.pruebatecnicaasd.core.URL_NO_POSTER
import com.example.pruebatecnicaasd.peliculas.data.model.DetallesPeliculaDTO
import com.example.pruebatecnicaasd.peliculas.data.model.GenerosPeliculaDTO
import com.example.pruebatecnicaasd.peliculas.domain.model.DetallesPeliculaDomain

fun DetallesPeliculaDTO.fromDetallesPeliculaDTOToDetallesPeliculaDomain() : DetallesPeliculaDomain {
    val url = if (posterPath.isNullOrEmpty()) {
        URL_NO_POSTER
    } else {
        "https://image.tmdb.org/t/p/w500/${posterPath}"
    }

    return DetallesPeliculaDomain(
        id = id,
        titulo = titulo ?: "",
        lema = lema ?: "",
        urlCartel = url,
        descripcion = descripcion ?: "Esta película no tiene descripción.",
        generos = generos.toNameString(),
        calificacion = (calificacion ?: 0f) * 10
    )
}

private fun List<GenerosPeliculaDTO>?.toNameString() : List<String> =
    this?.map { it.nombre }?: listOf()

