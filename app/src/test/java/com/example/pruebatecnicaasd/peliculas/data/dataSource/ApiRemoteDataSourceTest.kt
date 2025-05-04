package com.example.pruebatecnicaasd.peliculas.data.dataSource

import com.example.pruebatecnicaasd.core.network.PeliculasApi
import com.example.pruebatecnicaasd.peliculas.data.model.DetallesPeliculaDTO
import com.example.pruebatecnicaasd.peliculas.data.model.GenerosPeliculaDTO
import com.example.pruebatecnicaasd.peliculas.data.model.PeliculaPopularDTO
import com.example.pruebatecnicaasd.peliculas.data.model.PeliculasPopularesResponseDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ApiRemoteDataSourceTest {
    private lateinit var apiRemoteDataSource: ApiRemoteDataSource
    private val api: PeliculasApi = mockk()

    @Before
    fun setup() {
        apiRemoteDataSource = ApiRemoteDataSource(
            api = api
        )
    }

    @Test
    fun `getDetallesPelicula retorna una respuesta successful desde la api`() = runTest {
        //Given
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
            api.getDetallesPelicula(any())
        } returns Response.success(responseFake)

        //When
        val response = api.getDetallesPelicula(idFake)

        //Then
        assertEquals(responseFake, response.body())
        coVerify { api.getDetallesPelicula(idFake) }
    }

    @Test
    fun `getPeliculasPaginadas retorna una respuesta successful desde la api`() = runTest {
        //Given
        val idPaginaFake = 1
        val peliculasFake = listOf(
            PeliculaPopularDTO(
                id = 1,
                titulo = "Titulos fake",
                descripcion = "descripcion fake",
                posterPath = "url fake"
            )
        )
        val responseFake = PeliculasPopularesResponseDTO(
            page = idPaginaFake,
            peliculas = peliculasFake
        )
        coEvery {
            api.getPeliculasPaginadas(any())
        } returns Response.success(responseFake)

        //When
        val response = api.getPeliculasPaginadas(idPaginaFake)

        //Then
        assertEquals(responseFake, response.body())
        coVerify { api.getPeliculasPaginadas(idPaginaFake) }
    }


    @Test
    fun `getPeliculasPaginadas retorna una respuesta error desde la api`() = runTest {
        //Given
        val idPaginaFake = 1
        val errorBodyFake = "".toResponseBody("application/json".toMediaTypeOrNull())

        coEvery {
            api.getPeliculasPaginadas(any())
        } returns Response.error(400, errorBodyFake)

        //When
        val response = api.getPeliculasPaginadas(idPaginaFake)

        //Then
        assertFalse(response.isSuccessful)
        coVerify { api.getPeliculasPaginadas(idPaginaFake) }
    }

    @Test
    fun `getDetallesPelicula retorna una respuesta error desde la api`() = runTest {
        //Given
        val idFake = 1
        val errorBodyFake = "".toResponseBody("application/json".toMediaTypeOrNull())

        coEvery {
            api.getDetallesPelicula(any())
        } returns Response.error(400, errorBodyFake)

        //When
        val response = api.getDetallesPelicula(idFake)

        //Then
        assertFalse(response.isSuccessful)
        coVerify { api.getDetallesPelicula(idFake) }
    }
}