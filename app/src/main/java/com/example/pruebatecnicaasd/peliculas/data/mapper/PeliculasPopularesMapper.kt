package com.example.pruebatecnicaasd.peliculas.data.mapper

import com.example.pruebatecnicaasd.core.URL_NO_POSTER
import com.example.pruebatecnicaasd.peliculas.data.model.PeliculasPopularesResponseDTO
import com.example.pruebatecnicaasd.peliculas.domain.model.PeliculaPopularDomain

fun PeliculasPopularesResponseDTO.fromPeliculasPopularesResponseDTOToListPeliculaPopularDomain() : List<PeliculaPopularDomain> {
    val peliculasPopulares = mutableListOf<PeliculaPopularDomain>()

    peliculas.forEach { peliculaDTO ->
        val url = if (peliculaDTO.posterPath.isNullOrEmpty()) {
            URL_NO_POSTER
        } else {
            "https://image.tmdb.org/t/p/w400/${peliculaDTO.posterPath}"
        }
        val peliculaDomain = PeliculaPopularDomain(
            id = peliculaDTO.id,
            titulo = peliculaDTO.titulo ?: "",
            descripcion = peliculaDTO.descripcion ?: "Esta película no tiene descripción.",
            urlCartel = url
        )
        peliculasPopulares.add(peliculaDomain)
    }
    return peliculasPopulares
}