package com.example.pruebatecnicaasd.core.network

import com.example.pruebatecnicaasd.peliculas.data.model.PeliculasPopularesResponseDTO
import com.example.pruebatecnicaasd.peliculas.data.model.DetallesPeliculaDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeliculasApi {
    @GET("popular")
    suspend fun getPeliculasPaginadas(
        @Query("page") pagina: Int
    ): Response<PeliculasPopularesResponseDTO>

    @GET("{id}")
    suspend fun getDetallesPelicula(@Path("id") id: Int): Response<DetallesPeliculaDTO>
}