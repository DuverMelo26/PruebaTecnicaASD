package com.example.pruebatecnicaasd.peliculas.data.repository

import com.example.pruebatecnicaasd.R
import com.example.pruebatecnicaasd.peliculas.data.mapper.PeliculasPopularesMapper
import com.example.pruebatecnicaasd.peliculas.domain.model.PeliculaPopularDomain
import com.example.pruebatecnicaasd.core.network.ApiResponseStatus
import com.example.pruebatecnicaasd.peliculas.data.dataSource.ApiRemoteDataSource
import com.example.pruebatecnicaasd.peliculas.data.mapper.DetallesPeliculaMapper
import com.example.pruebatecnicaasd.peliculas.domain.model.DetallesPeliculaDomain
import com.example.pruebatecnicaasd.peliculas.domain.repository.PeliculasRemoteRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PeliculasRemoteRepository @Inject constructor(
    private val apiRemoteDataSource: ApiRemoteDataSource,
    private val detallesPeliculaMapper: DetallesPeliculaMapper,
    private val peliculasPopularesMapper: PeliculasPopularesMapper
) : PeliculasRemoteRepositoryInterface {
    override suspend fun getDetallesPelicula(id: Int): ApiResponseStatus<DetallesPeliculaDomain> = withContext(Dispatchers.IO) {
        try {
            val call = apiRemoteDataSource.getDetallesPelicula(id)
            if (!call.isSuccessful) return@withContext ApiResponseStatus.Error(R.string.no_fue_posible_obtener_detalles)
            if (call.body() == null) return@withContext ApiResponseStatus.Error(R.string.no_fue_posible_obtener_detalles)
            val peliculas = detallesPeliculaMapper.fromDetallesPeliculaDTOToDetallesPeliculaDomain(
                call.body()!!
            )
            ApiResponseStatus.Success(peliculas)
        } catch (e : Exception) {
            ApiResponseStatus.Error(R.string.no_fue_posible_obtener_detalles)
        }
    }

    override suspend fun getPeliculasPaginadas(pagina: Int) : ApiResponseStatus<List<PeliculaPopularDomain>> =
        withContext(Dispatchers.IO) {
            try {
                val call = apiRemoteDataSource.getPeliculasPaginadas(pagina)
                if (!call.isSuccessful) return@withContext ApiResponseStatus.Error(R.string.falla_consultando_peliculas)
                if (call.body() == null) return@withContext ApiResponseStatus.Error(R.string.falla_consultando_peliculas)
                val peliculas =
                    peliculasPopularesMapper.fromPeliculasPopularesResponseDTOToListPeliculaPopularDomain(
                        call.body()!!
                    )
                ApiResponseStatus.Success(peliculas)
            } catch (e: Exception) {
                ApiResponseStatus.Error(R.string.falla_consultando_peliculas)
            }
        }
}