package com.example.pruebatecnicaasd.peliculas.data.repository

import com.example.pruebatecnicaasd.core.network.ApiResponseStatus
import com.example.pruebatecnicaasd.peliculas.data.dataSource.ApiRemoteDataSource
import com.example.pruebatecnicaasd.peliculas.data.model.DetallesPeliculaDTO
import com.example.pruebatecnicaasd.peliculas.data.model.GenerosPeliculaDTO
import com.example.pruebatecnicaasd.peliculas.data.model.PeliculaPopularDTO
import com.example.pruebatecnicaasd.peliculas.data.model.PeliculasPopularesResponseDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PeliculasRemoteRepositoryTest {
    private lateinit var peliculasRemoteRepository: PeliculasRemoteRepository

    private var apiRemoteDataSource: ApiRemoteDataSource = mockk()

    @Before
    fun setup() {
        peliculasRemoteRepository = PeliculasRemoteRepository(
            apiRemoteDataSource = apiRemoteDataSource
        )
    }

    @Test
    fun `getDetallesPelicula retorna los detalles de una pelicula cuando la API retorna successful`() = runTest {
        // Given
        val idFake = 1
        val nombreGeneroFalso = "Genero"
        val listGenerosFake = listOf(GenerosPeliculaDTO(id = 1, nombre = nombreGeneroFalso))
        val responseFake = DetallesPeliculaDTO(
            titulo = "Titulo falso",
            posterPath = "Url falsa",
            id = idFake,
            descripcion = "Descripción falsa",
            generos = listGenerosFake,
            calificacion = 7.5f,
            lema = ""
        )
        coEvery {
            apiRemoteDataSource.getDetallesPelicula(any())
        } returns Response.success(responseFake)

        //When
        val response = peliculasRemoteRepository.getDetallesPelicula(idFake)

        //Then
        assertTrue(response is ApiResponseStatus.Success)
        val data = (response as ApiResponseStatus.Success).data
        assertEquals(idFake, data.id)
        assertEquals(nombreGeneroFalso, data.generos[0])
    }

    @Test
    fun `getDetallesPelicula retorna un error cuando la API retorna que no fue successful por error 404`() = runTest {
        //Given
        val idFake = 1
        val errorBodyFake = "".toResponseBody("application/json".toMediaTypeOrNull())
        coEvery {
            apiRemoteDataSource.getDetallesPelicula(any())
        } returns Response.error(404, errorBodyFake)

        //When
        val response = peliculasRemoteRepository.getDetallesPelicula(idFake)

        //Then
        assertTrue(response is ApiResponseStatus.Error)
    }

    @Test
    fun `getDetallesPelicula retorna error cuando la API retorna successful pero el body es nulo`() = runTest {
        //Given
        val idFake = 1
        coEvery {
            apiRemoteDataSource.getDetallesPelicula(any())
        } returns Response.success(null)

        //When
        val response = peliculasRemoteRepository.getDetallesPelicula(idFake)

        //Then
        assertTrue(response is ApiResponseStatus.Error)
    }

    @Test
    fun `getDetallesPelicula retorna error cuando la API provoca una excepcion`() = runTest {
        //Given
        val idFake = 1
        coEvery {
            apiRemoteDataSource.getDetallesPelicula(any())
        } throws RuntimeException("Error de red")

        //When
        val response = peliculasRemoteRepository.getDetallesPelicula(idFake)

        //Then
        assertTrue(response is ApiResponseStatus.Error)
    }

    @Test
    fun `getPeliculasPaginadas retorna error cuando la API provoca una excepcion`() = runTest {
        //Given
        val idPaginaFake = 1
        coEvery {
            apiRemoteDataSource.getPeliculasPaginadas(any())
        } throws RuntimeException("Error de red")

        //When
        val response = peliculasRemoteRepository.getPeliculasPaginadas(idPaginaFake)

        //Then
        assertTrue(response is ApiResponseStatus.Error)
    }

    @Test
    fun `getPeliculasPaginadas retorna error cuando la API retorna sucessfull pero el body es nulo`() = runTest {
        //Given
        val idPaginaFake = 1
        coEvery {
            apiRemoteDataSource.getPeliculasPaginadas(any())
        } returns Response.success(null)

        //When
        val response = peliculasRemoteRepository.getPeliculasPaginadas(idPaginaFake)

        //Then
        assertTrue(response is ApiResponseStatus.Error)
    }

    @Test
    fun `getPeliculasPaginadas retorna un error cuando la API retorna que no fue successful por error 404`() = runTest {
        //Given
        val idPaginaFake = 1
        val errorBodyFake = "".toResponseBody("application/json".toMediaTypeOrNull())
        coEvery {
            apiRemoteDataSource.getPeliculasPaginadas(any())
        } returns Response.error(404, errorBodyFake)

        //When
        val response = peliculasRemoteRepository.getPeliculasPaginadas(idPaginaFake)

        //Then
        assertTrue(response is ApiResponseStatus.Error)
    }

    @Test
    fun `getPeliculasPaginadas retorna lista de peliculas cuando la API retorna successful`() = runTest {
        //Given
        val idPaginaFake = 1
        val peliculasFake = listOf(
            PeliculaPopularDTO(
                id = 1,
                titulo = "Titulos fake",
                descripcion = "descripcion fake",
                posterPath = "url fake"
            ),
            PeliculaPopularDTO(
                id = 2,
                titulo = null,
                descripcion = null,
                posterPath = null
            ),
            PeliculaPopularDTO(
                id = 3,
                titulo = "",
                descripcion = "",
                posterPath = ""
            )
        )
        val responseFake = PeliculasPopularesResponseDTO(
            page = idPaginaFake,
            peliculas = peliculasFake
        )
        coEvery {
            apiRemoteDataSource.getPeliculasPaginadas(any())
        } returns Response.success(responseFake)

        //When
        val response = peliculasRemoteRepository.getPeliculasPaginadas(idPaginaFake)

        //Then
        assertTrue(response is ApiResponseStatus.Success)
        val data = (response as ApiResponseStatus.Success).data
        assertEquals(3, data.size)
        assertTrue(data[1].urlCartel.isNotEmpty())
    }

    @Test
    fun `getDetallesPelicula retorna algunos detalles de una pelicula nulos cuando la API retorna successful`() = runTest {
        //Given
        val idFake = 1
        val responseFake = DetallesPeliculaDTO(
            titulo = null,
            posterPath = null,
            id = idFake,
            descripcion = null,
            generos = null,
            calificacion = null,
            lema = null
        )
        coEvery {
            apiRemoteDataSource.getDetallesPelicula(any())
        } returns Response.success(responseFake)

        //When
        val response = peliculasRemoteRepository.getDetallesPelicula(idFake)

        //Then
        assertTrue(response is ApiResponseStatus.Success)
        val data = (response as ApiResponseStatus.Success).data
        assertEquals(idFake, data.id)
        assertEquals(0, data.generos.size)
    }
}