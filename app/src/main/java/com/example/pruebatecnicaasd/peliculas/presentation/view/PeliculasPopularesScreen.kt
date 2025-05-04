package com.example.pruebatecnicaasd.peliculas.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pruebatecnicaasd.R
import com.example.pruebatecnicaasd.peliculas.domain.model.PeliculaPopularDomain
import com.example.pruebatecnicaasd.peliculas.presentation.viewModel.PeliculasViewModel
import com.example.pruebatecnicaasd.core.network.ApiResponseStatus

@Composable
fun PeliculasPopularesScreen(
    peliculasViewModel: PeliculasViewModel,
    onClickPelicula: (Int) -> Unit
) {
    val searchPelicula = remember { mutableStateOf("") }
    val peliculasPopulares = peliculasViewModel.peliculasPopulares
    val listState = rememberLazyListState()
    val apiStatus = peliculasViewModel.getPeliculasPaginadasStatus.observeAsState()

    LaunchedEffect(Unit) {
        snapshotFlow { listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size }
            .collect { visibleItems ->
                if (peliculasViewModel.peliculasPopulares.isEmpty()) {
                    peliculasViewModel.getMovies()
                    return@collect
                }

                if (visibleItems >= peliculasPopulares.size && apiStatus.value !is ApiResponseStatus.Loading
                ) {
                    peliculasViewModel.getMovies()
                }
            }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (peliculasPopulares.isNotEmpty()) {
            BuscadorPeliculas(searchPelicula = searchPelicula)
        } else {
            LoaderBar(modifier = Modifier.fillMaxSize())
        }

        ListaPeliculas(
            listState = listState,
            peliculasPopulares = peliculasPopulares,
            onClickPelicula = onClickPelicula,
            searchPelicula = searchPelicula,
            apiStatus = apiStatus.value
        )
    }
}

@Composable
fun ListaPeliculas(
    listState: LazyListState,
    peliculasPopulares: List<PeliculaPopularDomain>,
    onClickPelicula: (Int) -> Unit,
    searchPelicula: MutableState<String>,
    apiStatus: ApiResponseStatus<List<PeliculaPopularDomain>>?
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize().testTag("ListaPeliculas")
    ) {
        items(
            peliculasPopulares.filter { it.titulo.contains(searchPelicula.value, true) }
        ) { pelicula ->
            ItemPelicula(pelicula = pelicula, onClick = { onClickPelicula(pelicula.id) })
        }

        item {
            when (apiStatus) {
                is ApiResponseStatus.Error -> AlertView(apiStatus.message)
                is ApiResponseStatus.Loading -> LoaderBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                )

                else -> {}
            }
        }
    }
}

@Composable
private fun LoaderBar(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colorResource(R.color.purple_500),
            modifier = Modifier.testTag("LoaderPeliculas")
        )
    }
}

@Composable
private fun AlertView(message: Int) {
    Text(
        text = stringResource(id = message),
        color = Color.Red,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun BuscadorPeliculas(searchPelicula: MutableState<String>) {
    OutlinedTextField(
        value = searchPelicula.value,
        onValueChange = { searchPelicula.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .testTag("BuscadorPeliculas"),
        placeholder = {
            Text(
                text = stringResource(R.string.escriba_el_nombre_de_la_pelicula),
                color = Color.Black
            )
        },
        leadingIcon = {
            Icon(Icons.Rounded.Search, contentDescription = "Buscar película", tint = Color.Black)
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color.Black,
            focusedLeadingIconColor = Color.Black,
            unfocusedLeadingIconColor = Color.Black,
            focusedPlaceholderColor = Color.Black,
            unfocusedPlaceholderColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black
        )
    )
}

@Composable
fun ItemPelicula(pelicula: PeliculaPopularDomain, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
            .testTag("ItemPelicula_${pelicula.id}")
    ) {
        Image(
            painter = rememberAsyncImagePainter(pelicula.urlCartel),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            Modifier.height(150.dp)
        ) {
            Text(
                text = pelicula.titulo,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp
            )
            Text(
                text = pelicula.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                fontSize = 13.sp
            )
        }
    }
}