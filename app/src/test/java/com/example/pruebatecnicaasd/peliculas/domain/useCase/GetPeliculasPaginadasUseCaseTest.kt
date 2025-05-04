package com.example.pruebatecnicaasd.peliculas.domain.useCase

import com.example.pruebatecnicaasd.core.network.ApiResponseStatus
import com.example.pruebatecnicaasd.peliculas.domain.model.PeliculaPopularDomain
import com.example.pruebatecnicaasd.peliculas.domain.repository.PeliculasRemoteRepositoryInterface
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPeliculasPaginadasUseCaseTest {
    private val peliculasRemoteRepositoryInterface: PeliculasRemoteRepositoryInterface = mockk()
    private lateinit var getPeliculasPaginadasUseCase: GetPeliculasPaginadasUseCase

    @Before
    fun setup() {
        getPeliculasPaginadasUseCase = GetPeliculasPaginadasUseCase(peliculasRemoteRepositoryInterface)
    }

    @Test
    fun `useCase debe devolver success cuendo el repositorio devuelva el listado de peliculas populares correctamente`() =
        runTest {
            //Given
            val idPaginaFake = 10
            val fakeListaPelicula = listOf(
                PeliculaPopularDomain(
                    titulo = "Pelicula falsa",
                    urlCartel = "Url falsa",
                    id = 1,
                    descripcion = "Descripcion falsa"
                ),
                PeliculaPopularDomain(
                    titulo = "Pelicula falsa",
                    urlCartel = "Url falsa",
                    id = 2,
                    descripcion = "Descripcion falsa"
                )
            )
            coEvery {
                peliculasRemoteRepositoryInterface.getPeliculasPaginadas(idPaginaFake)
            } returns ApiResponseStatus.Success(fakeListaPelicula)

            //When
            val result = getPeliculasPaginadasUseCase(pagina = idPaginaFake)

            //Assert
            assertTrue(result is ApiResponseStatus.Success)
            val data = (result as ApiResponseStatus.Success).data
            assertEquals(2, data.size)
            assertEquals(2, data[1].id)
        }

    @Test
    fun `useCase debe devolver error y un id de mensaje cuando el repositorio falle al retornar la lista de peliculas populares`() =
        runTest {
            //Given
            val messageFake = 1
            val idPaginaFake = 10

            coEvery {
                peliculasRemoteRepositoryInterface.getPeliculasPaginadas(idPaginaFake)
            } returns ApiResponseStatus.Error(messageFake)

            //When
            val result = getPeliculasPaginadasUseCase(idPaginaFake)

            //Assert
            assertTrue(result is ApiResponseStatus.Error)
            assertEquals(messageFake, (result as ApiResponseStatus.Error).message)
        }
}