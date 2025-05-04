package com.example.pruebatecnicaasd.peliculas.domain.useCase

import com.example.pruebatecnicaasd.peliculas.domain.model.PeliculaPopularDomain
import com.example.pruebatecnicaasd.core.network.ApiResponseStatus
import com.example.pruebatecnicaasd.peliculas.domain.repository.PeliculasRemoteRepositoryInterface
import javax.inject.Inject

class GetPeliculasPaginadasUseCase @Inject constructor(
    private val peliculasRemoteRepositoryInterface: PeliculasRemoteRepositoryInterface
) {
    suspend operator fun invoke(pagina: Int) : ApiResponseStatus<List<PeliculaPopularDomain>> = peliculasRemoteRepositoryInterface.getPeliculasPaginadas(pagina)
}