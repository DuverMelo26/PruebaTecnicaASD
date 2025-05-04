package com.example.pruebatecnicaasd.peliculas.domain.useCase

import com.example.pruebatecnicaasd.core.network.ApiResponseStatus
import com.example.pruebatecnicaasd.peliculas.domain.model.DetallesPeliculaDomain
import com.example.pruebatecnicaasd.peliculas.domain.repository.PeliculasRemoteRepositoryInterface
import javax.inject.Inject

class GetDetallesPeliculaUseCase @Inject constructor(
    private val peliculasRemoteRepositoryInterface: PeliculasRemoteRepositoryInterface
) {
    suspend operator fun invoke(id: Int) : ApiResponseStatus<DetallesPeliculaDomain> =
        peliculasRemoteRepositoryInterface.getDetallesPelicula(id)
}