package com.diego.matesanz.arcaneum.ui.navigation.bottombar.tabs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.diego.matesanz.arcaneum.ui.navigation.BookDetail
import com.diego.matesanz.arcaneum.ui.navigation.Camera
import com.diego.matesanz.arcaneum.ui.navigation.Explore
import com.diego.matesanz.arcaneum.ui.navigation.Home
import com.diego.matesanz.arcaneum.ui.screens.bookDetail.view.BookDetailScreen
import com.diego.matesanz.arcaneum.ui.screens.camera.view.CameraScreen
import com.diego.matesanz.arcaneum.ui.screens.home.view.HomeScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.exploreTab(navController: NavHostController) {
    navigation<Explore>(startDestination = Home) {
        composable<Home> {
            HomeScreen(
                onBookClick = { book -> navController.navigate(BookDetail(book.bookId)) },
                onCamClick = { navController.navigate(Camera) },
                viewModel = koinViewModel(),
            )
        }
        composable<BookDetail> { backStackEntry ->
            val bookDetail: BookDetail = backStackEntry.toRoute()
            BookDetailScreen(
                onBack = { navController.popBackStack() },
                viewModel = koinViewModel(parameters = { parametersOf(bookDetail.bookId) }),
            )
        }
        composable<Camera> {
            CameraScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navController.navigate(BookDetail(book.bookId)) },
                viewModel = koinViewModel(),
            )
        }
    }
}
