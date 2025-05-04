package com.example.pruebatecnicaasd.peliculas.domain.repository

import com.example.pruebatecnicaasd.peliculas.domain.model.PeliculaPopularDomain
import com.example.pruebatecnicaasd.core.network.ApiResponseStatus
import com.example.pruebatecnicaasd.peliculas.domain.model.DetallesPeliculaDomain

interface PeliculasRemoteRepositoryInterface {
    suspend fun getDetallesPelicula(id: Int): ApiResponseStatus<DetallesPeliculaDomain>

    suspend fun getPeliculasPaginadas(pagina: Int) : ApiResponseStatus<List<PeliculaPopularDomain>>
}