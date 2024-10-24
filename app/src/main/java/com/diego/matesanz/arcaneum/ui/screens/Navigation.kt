package com.diego.matesanz.arcaneum.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.diego.matesanz.arcaneum.ui.screens.camera.CameraScreen
import com.diego.matesanz.arcaneum.ui.screens.detail.DetailScreen
import com.diego.matesanz.arcaneum.ui.screens.detail.DetailViewModel
import com.diego.matesanz.arcaneum.ui.screens.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onBookClick = { book -> navController.navigate("detail/${book.id}") },
                onCamClick = { navController.navigate("camera") },
                onBookmarked = { book -> /* TODO: Save book */ },
            )
        }
        composable(
            route = "detail/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = requireNotNull(backStackEntry.arguments?.getString("bookId"))
            DetailScreen(
                viewModel = viewModel { DetailViewModel(bookId) },
                onBack = { navController.popBackStack() },
                onBookmarked = { book -> /* TODO: Save book */ },
            )
        }
        composable("camera") {
            CameraScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navController.navigate("detail/${book.id}") },
            )
        }
    }
}
