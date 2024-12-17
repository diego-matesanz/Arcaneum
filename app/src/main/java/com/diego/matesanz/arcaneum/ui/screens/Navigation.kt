package com.diego.matesanz.arcaneum.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.diego.matesanz.arcaneum.App
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import com.diego.matesanz.arcaneum.data.datasource.BooksLocalDataSource
import com.diego.matesanz.arcaneum.data.datasource.BooksRemoteDataSource
import com.diego.matesanz.arcaneum.data.datasource.ShelvesLocalDataSource
import com.diego.matesanz.arcaneum.ui.screens.Screen.Camera
import com.diego.matesanz.arcaneum.ui.screens.Screen.Detail
import com.diego.matesanz.arcaneum.ui.screens.Screen.Home
import com.diego.matesanz.arcaneum.ui.screens.camera.view.CameraScreen
import com.diego.matesanz.arcaneum.ui.screens.camera.viewModel.CameraViewModel
import com.diego.matesanz.arcaneum.ui.screens.detail.view.DetailScreen
import com.diego.matesanz.arcaneum.ui.screens.detail.viewModel.DetailViewModel
import com.diego.matesanz.arcaneum.ui.screens.home.view.HomeScreen
import com.diego.matesanz.arcaneum.ui.screens.home.viewModel.HomeViewModel
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {
    val app = LocalContext.current.applicationContext as App
    val navController = rememberNavController()
    val booksRepository = BooksRepository(
        BooksRemoteDataSource(),
        BooksLocalDataSource(app.db.booksDao()),
    )
    val shelvesRepository = ShelvesRepository(ShelvesLocalDataSource(app.db.shelvesDao()))

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(
                onBookClick = { book -> navController.navigate(Detail(book.bookId)) },
                onCamClick = { navController.navigate(Camera) },
                viewModel = viewModel { HomeViewModel(booksRepository) },
            )
        }
        composable<Detail> { backStackEntry ->
            val detail: Detail = backStackEntry.toRoute()
            DetailScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel {
                    DetailViewModel(
                        id = detail.bookId,
                        booksRepository = booksRepository,
                        shelvesRepository = shelvesRepository,
                    )
                },
            )
        }
        composable<Camera> {
            CameraScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navController.navigate(Detail(book.bookId)) },
                viewModel = viewModel { CameraViewModel(booksRepository) },
            )
        }
    }
}

private sealed class Screen {
    @Serializable
    object Home : Screen()

    @Serializable
    data class Detail(val bookId: String) : Screen()

    @Serializable
    object Camera : Screen()
}
