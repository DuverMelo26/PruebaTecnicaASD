package com.example.pruebatecnicaasd.peliculas.presentation

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pruebatecnicaasd.peliculas.presentation.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navegarPeliculaPopularesScreenToDetallesPeliculasScreen() {
        val nombrePelicula = "al"

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithTag("LoaderPeliculas").fetchSemanticsNodes().isEmpty()
        }

        composeTestRule.onAllNodesWithTag("BuscadorPeliculas")
            .fetchSemanticsNodes().isNotEmpty()

        repeat(3) {
            composeTestRule.onNodeWithTag("ListaPeliculas")
                .performTouchInput {
                    swipeUp()
                }
            composeTestRule.waitForIdle()
        }

        nombrePelicula.forEach { caracter ->
            composeTestRule.onNodeWithTag("BuscadorPeliculas")
                .performTextInput(caracter.toString())
            Thread.sleep(300)
        }

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodes(hasTestTagStartingWith("ItemPelicula_")).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onAllNodes(hasTestTagStartingWith("ItemPelicula_"))
            .onFirst()
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithTag("LoaderDetallesPelicula").fetchSemanticsNodes().isEmpty()
        }
        Thread.sleep(2000)
        composeTestRule.onNodeWithTag("SalirDetalles").performClick()

        composeTestRule.waitUntil(timeoutMillis = 10_000) {
            composeTestRule.onAllNodesWithTag("BuscadorPeliculas").fetchSemanticsNodes().isNotEmpty()
        }
        Thread.sleep(2000)
    }

    private fun hasTestTagStartingWith(prefix: String): SemanticsMatcher {
        return SemanticsMatcher(
            "TestTag starts with \"$prefix\""
        ) { node ->
            val tag = node.config.getOrNull(SemanticsProperties.TestTag)
            tag?.startsWith(prefix) == true
        }
    }
}