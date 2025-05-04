package com.example.pruebatecnicaasd.peliculas.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pruebatecnicaasd.R
import com.example.pruebatecnicaasd.core.network.ApiResponseStatus
import com.example.pruebatecnicaasd.peliculas.domain.model.DetallesPeliculaDomain
import com.example.pruebatecnicaasd.peliculas.presentation.viewModel.PeliculasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesPeliculaScreen(
    id: Int,
    peliculasViewModel: PeliculasViewModel,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val status by peliculasViewModel.getDetallesPeliculaStatus.observeAsState()
    val detallesPelicula = peliculasViewModel.detallesPelicula.value

    LaunchedEffect(id) {
        peliculasViewModel.getDetallesPelicula(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onBackClick, modifier = Modifier.testTag("SalirDetalles")) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.purple_500))
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .background(Color.White)
                .fillMaxSize()
        ) {
            when (status) {
                is ApiResponseStatus.Error -> ErrorScreen((status as ApiResponseStatus.Error<DetallesPeliculaDomain>).message)
                is ApiResponseStatus.Loading -> LoaderScreen()
                is ApiResponseStatus.Success -> DetallesScreen(
                    detallesPelicula = detallesPelicula, scrollState = scrollState
                )
                else -> {}
            }
        }
    }


}

@Composable
private fun DetallesScreen(detallesPelicula: DetallesPeliculaDomain?, scrollState: ScrollState) {
    if (detallesPelicula != null) {
        val porcentajeCalificacion = detallesPelicula.calificacion.toInt()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)
            ) {


                Image(
                    painter = rememberAsyncImagePainter(detallesPelicula.urlCartel),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(100.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                )
                            )
                        )
                )
                Text(
                    text = detallesPelicula.titulo,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(
                            Color.White.copy(alpha = 0.9f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$porcentajeCalificacion%",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Me gusta",
                        tint = Color(0xFFFFD700), // Color dorado
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (detallesPelicula.lema.isNotEmpty()) {
                Text(
                    text = detallesPelicula.lema,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

            }

            Text(
                text = detallesPelicula.descripcion,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                detallesPelicula.generos.forEach { genero ->
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(40.dp))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = genero,
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(26.dp))

        }
    }
}

@Composable
private fun ErrorScreen(message: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(message),
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoaderScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colorResource(R.color.purple_500),
            modifier = Modifier.testTag("LoaderDetallesPelicula")
        )
    }
}