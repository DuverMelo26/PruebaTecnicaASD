package com.example.pruebatecnicaasd.peliculas.domain.useCase

import com.example.pruebatecnicaasd.core.network.ApiResponseStatus
import com.example.pruebatecnicaasd.peliculas.domain.model.DetallesPeliculaDomain
import com.example.pruebatecnicaasd.peliculas.domain.repository.PeliculasRemoteRepositoryInterface
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetDetallesPeliculaUseCaseTest {

    private val peliculasRemoteRepositoryInterface: PeliculasRemoteRepositoryInterface = mockk()
    private lateinit var getDetallesPeliculaUseCase: GetDetallesPeliculaUseCase


    @Before
    fun setup() {
        getDetallesPeliculaUseCase = GetDetallesPeliculaUseCase(peliculasRemoteRepositoryInterface)
    }

    @Test
    fun `useCase debe devolver success cuendo el repositorio devuelva los detalles de la pelicula correctamente`() =
        runTest {
            //Given
            val idMovieFake = 10
            val fakeDetallesPelicula = DetallesPeliculaDomain(
                titulo = "Pelicula falsa",
                urlCartel = "Url falsa",
                id = idMovieFake,
                descripcion = "Descripcion falsa",
                generos = listOf("Ficcion"),
                calificacion = 7.5f,
                lema = "Lema falso"
            )
            coEvery {
                peliculasRemoteRepositoryInterface.getDetallesPelicula(idMovieFake)
            } returns ApiResponseStatus.Success(fakeDetallesPelicula)

            //When
            val result = getDetallesPeliculaUseCase(idMovieFake)

            //Assert
            assertTrue(result is ApiResponseStatus.Success)
            assertEquals(idMovieFake, (result as ApiResponseStatus.Success).data.id)

        }

    @Test
    fun `useCase debe devolver error y un id de mensaje cuando el repositorio falle al devolver los detalles de la pelicula`() =
        runTest {
            //Given
            val messageFake = 1
            val idMovieFake = 10

            coEvery {
                peliculasRemoteRepositoryInterface.getDetallesPelicula(idMovieFake)
            } returns ApiResponseStatus.Error(messageFake)

            //When
            val result = getDetallesPeliculaUseCase(idMovieFake)

            //Assert
            assertTrue(result is ApiResponseStatus.Error)
            assertEquals(messageFake, (result as ApiResponseStatus.Error).message)
        }
}