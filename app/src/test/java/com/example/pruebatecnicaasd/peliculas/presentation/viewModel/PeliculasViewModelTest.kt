package com.example.pruebatecnicaasd.peliculas.presentation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pruebatecnicaasd.core.network.ApiResponseStatus
import com.example.pruebatecnicaasd.peliculas.domain.model.DetallesPeliculaDomain
import com.example.pruebatecnicaasd.peliculas.domain.model.PeliculaPopularDomain
import com.example.pruebatecnicaasd.peliculas.domain.useCase.GetDetallesPeliculaUseCase
import com.example.pruebatecnicaasd.peliculas.domain.useCase.GetPeliculasPaginadasUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PeliculasViewModelTest {
    private var getPeliculasPaginadasUseCase: GetPeliculasPaginadasUseCase = mockk()
    private var getDetallesPeliculaUseCase: GetDetallesPeliculaUseCase = mockk()
    private lateinit var peliculasViewModel: PeliculasViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        peliculasViewModel =
            PeliculasViewModel(getPeliculasPaginadasUseCase, getDetallesPeliculaUseCase)
    }

    @Test
    fun `getMovies trae la primera pagina de peliculas exitosamente`() = runTest {
        // Given
        val peliculasFake = listOf(
            PeliculaPopularDomain(
                titulo = "Pelicula falsa",
                urlCartel = "Url falsa",
                id = 1,
                descripcion = "Descripcion falsa"
            )
        )

        coEvery { getPeliculasPaginadasUseCase(any()) } returns ApiResponseStatus.Success(
            peliculasFake
        )
        //When
        peliculasViewModel.getMovies()

        //Then
        assertTrue(peliculasViewModel.getPeliculasPaginadasStatus.value is ApiResponseStatus.Success)
        assertEquals(1, peliculasViewModel.peliculasPopulares.size)
    }

    @Test
    fun `getMovies falla al traer las peliculas populares y trae el id de un mensaje de error`() =
        runTest {
            // Given
            val idMessageFake = 1

            coEvery { getPeliculasPaginadasUseCase(any()) } returns ApiResponseStatus.Error(
                idMessageFake
            )
            //When
            peliculasViewModel.getMovies()

            //Then
            assertTrue(peliculasViewModel.getPeliculasPaginadasStatus.value is ApiResponseStatus.Error)
            assertEquals(
                1,
                (peliculasViewModel.getPeliculasPaginadasStatus.value as ApiResponseStatus.Error).message
            )
            assertEquals(0, peliculasViewModel.peliculasPopulares.size)
        }

    @Test
    fun `getDetallesPelicula falla al traer los detalles de una pelicula y trae el id de un mensaje de error`() =
        runTest {
            // Given
            val idMessageFake = 1
            val idPeliculaFake = 10

            coEvery { getDetallesPeliculaUseCase(any()) } returns ApiResponseStatus.Error(
                idMessageFake
            )
            //When
            peliculasViewModel.getDetallesPelicula(idPeliculaFake)

            //Then
            assertTrue(peliculasViewModel.getDetallesPeliculaStatus.value is ApiResponseStatus.Error)
            assertEquals(
                1,
                (peliculasViewModel.getDetallesPeliculaStatus.value as ApiResponseStatus.Error).message
            )
        }


    @Test
    fun `getDetallesPelicula trae los detalles de una pelicula exitosamente`() = runTest {
        // Given
        val idPeliculaFake = 1
        val peliculaFake = DetallesPeliculaDomain(
            titulo = "Pelicula falsa",
            urlCartel = "Url falsa",
            id = idPeliculaFake,
            descripcion = "Descripcion falsa",
            lema = "lema fake",
            generos = listOf("Genero fake"),
            calificacion = 7.5f
        )

        coEvery { getDetallesPeliculaUseCase(any()) } returns ApiResponseStatus.Success(
            peliculaFake
        )
        //When
        peliculasViewModel.getDetallesPelicula(idPeliculaFake)

        //Then
        assertTrue(peliculasViewModel.getDetallesPeliculaStatus.value is ApiResponseStatus.Success)
        assertEquals(idPeliculaFake, peliculasViewModel.detallesPelicula.value?.id ?: 0)
    }
}