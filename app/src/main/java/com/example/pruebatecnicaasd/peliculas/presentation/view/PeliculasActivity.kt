package com.example.pruebatecnicaasd.peliculas.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pruebatecnicaasd.peliculas.presentation.viewModel.PeliculasViewModel
import com.example.pruebatecnicaasd.peliculas.presentation.view.theme.PruebaTecnicaASDTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeliculasActivity : ComponentActivity() {

    private val peliculasViewModel: PeliculasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaTecnicaASDTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "peliculas_screen") {

                    composable("peliculas_screen") {
                        PeliculasPopularesScreen(
                            onClickPelicula = { id ->
                                navController.navigate("detalles/$id")
                            }, peliculasViewModel = peliculasViewModel
                        )
                    }

                    composable(
                        "detalles/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id") ?: 0
                        DetallesPeliculaScreen(
                            id = id,
                            peliculasViewModel = peliculasViewModel,
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}