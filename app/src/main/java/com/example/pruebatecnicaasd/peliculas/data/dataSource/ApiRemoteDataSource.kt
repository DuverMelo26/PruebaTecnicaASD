package com.example.pruebatecnicaasd.peliculas.data.dataSource

import com.example.pruebatecnicaasd.peliculas.data.model.PeliculasPopularesResponseDTO
import com.example.pruebatecnicaasd.core.network.PeliculasApi
import com.example.pruebatecnicaasd.peliculas.data.model.DetallesPeliculaDTO
import retrofit2.Response
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(
    private val api: PeliculasApi
) {
    suspend fun getDetallesPelicula(id: Int) : Response<DetallesPeliculaDTO> {
        return api.getDetallesPelicula(id)
    }

    suspend fun getPeliculasPaginadas(pagina: Int) : Response<PeliculasPopularesResponseDTO> {
        return api.getPeliculasPaginadas(pagina)
    }
}