package com.example.pruebatecnicaasd.peliculas.data.mapper

import com.example.pruebatecnicaasd.core.URL_NO_POSTER
import com.example.pruebatecnicaasd.peliculas.data.model.DetallesPeliculaDTO
import com.example.pruebatecnicaasd.peliculas.data.model.GenerosPeliculaDTO
import com.example.pruebatecnicaasd.peliculas.domain.model.DetallesPeliculaDomain
import javax.inject.Inject

class DetallesPeliculaMapper @Inject constructor() {
    fun fromDetallesPeliculaDTOToDetallesPeliculaDomain(
        detallesPeliculaDTO: DetallesPeliculaDTO
    ) : DetallesPeliculaDomain {
        val url = if (detallesPeliculaDTO.posterPath.isNullOrEmpty()) {
            URL_NO_POSTER
        } else {
            "https://image.tmdb.org/t/p/w500/${detallesPeliculaDTO.posterPath}"
        }

        return DetallesPeliculaDomain(
            id = detallesPeliculaDTO.id,
            titulo = detallesPeliculaDTO.titulo ?: "",
            lema = detallesPeliculaDTO.lema ?: "",
            urlCartel = url,
            descripcion = detallesPeliculaDTO.descripcion ?: "Esta película no tiene descripción.",
            generos = getListGeneros(detallesPeliculaDTO.generos ?: listOf()),
            calificacion = (detallesPeliculaDTO.calificacion ?: 0f) * 10
        )
    }

    private fun getListGeneros(generosDTO: List<GenerosPeliculaDTO>) : List<String> {
        val listaGeneros = mutableListOf<String>()
        generosDTO.forEach { generoDTO ->
            listaGeneros.add(generoDTO.nombre)
        }
        return listaGeneros
    }
}