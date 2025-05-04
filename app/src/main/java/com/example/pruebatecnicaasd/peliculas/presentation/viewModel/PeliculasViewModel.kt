package com.example.pruebatecnicaasd.peliculas.presentation.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicaasd.core.network.ApiResponseStatus
import com.example.pruebatecnicaasd.peliculas.domain.model.DetallesPeliculaDomain
import com.example.pruebatecnicaasd.peliculas.domain.model.PeliculaPopularDomain
import com.example.pruebatecnicaasd.peliculas.domain.useCase.GetDetallesPeliculaUseCase
import com.example.pruebatecnicaasd.peliculas.domain.useCase.GetPeliculasPaginadasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeliculasViewModel @Inject constructor(
    private val getPeliculasPaginadasUseCase: GetPeliculasPaginadasUseCase,
    private val getDetallesPeliculaUseCase: GetDetallesPeliculaUseCase
) : ViewModel() {
    private val _peliculasPopulares = mutableStateListOf<PeliculaPopularDomain>()
    val peliculasPopulares: List<PeliculaPopularDomain> get() = _peliculasPopulares

    private var actualPage = 1
    var getPeliculasPaginadasStatus = MutableLiveData<ApiResponseStatus<List<PeliculaPopularDomain>>>()
        private set

    val detallesPelicula = mutableStateOf<DetallesPeliculaDomain?>(null)

    var getDetallesPeliculaStatus = MutableLiveData<ApiResponseStatus<DetallesPeliculaDomain>>()
        private set

    fun getMovies() = viewModelScope.launch {
        getPeliculasPaginadasStatus.value = ApiResponseStatus.Loading()
        handleGetPeliculasPaginadas(getPeliculasPaginadasUseCase(actualPage))
    }

    private fun handleGetPeliculasPaginadas(apiResponse: ApiResponseStatus<List<PeliculaPopularDomain>>) {
        if (apiResponse is ApiResponseStatus.Success) {
            _peliculasPopulares.addAll(apiResponse.data)
            actualPage++
        }
        getPeliculasPaginadasStatus.value = apiResponse
    }

    fun getDetallesPelicula(id: Int) = viewModelScope.launch {
        getDetallesPeliculaStatus.value = ApiResponseStatus.Loading()
        handleGetDetallesPelicula(getDetallesPeliculaUseCase(id))
    }

    private fun handleGetDetallesPelicula(apiResponseStatus: ApiResponseStatus<DetallesPeliculaDomain>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            detallesPelicula.value = apiResponseStatus.data
        }
        getDetallesPeliculaStatus.value = apiResponseStatus
    }
}