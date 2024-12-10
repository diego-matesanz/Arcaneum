package com.diego.matesanz.arcaneum.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.diego.matesanz.arcaneum.ui.screens.Screen.Camera
import com.diego.matesanz.arcaneum.ui.screens.Screen.Detail
import com.diego.matesanz.arcaneum.ui.screens.Screen.Home
import com.diego.matesanz.arcaneum.ui.screens.camera.view.CameraScreen
import com.diego.matesanz.arcaneum.ui.screens.detail.view.DetailScreen
import com.diego.matesanz.arcaneum.ui.screens.detail.viewModel.DetailViewModel
import com.diego.matesanz.arcaneum.ui.screens.home.view.HomeScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(
                onBookClick = { book -> navController.navigate(Detail(book.id)) },
                onCamClick = { navController.navigate(Camera) },
            )
        }
        composable<Detail> { backStackEntry ->
            val detail: Detail = backStackEntry.toRoute()
            DetailScreen(
                viewModel = viewModel { DetailViewModel(detail.bookId) },
                onBack = { navController.popBackStack() },
            )
        }
        composable<Camera> {
            CameraScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navController.navigate(Detail(book.id)) },
            )
        }
    }
}

private sealed class Screen {
    @Serializable
    object Home: Screen()

    @Serializable
    data class Detail(val bookId: String): Screen()

    @Serializable
    object Camera: Screen()
}
