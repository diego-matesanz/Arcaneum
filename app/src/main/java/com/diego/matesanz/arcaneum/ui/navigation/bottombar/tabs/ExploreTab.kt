package com.diego.matesanz.arcaneum.ui.navigation.bottombar.tabs

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import com.diego.matesanz.arcaneum.ui.navigation.BookDetail
import com.diego.matesanz.arcaneum.ui.navigation.Camera
import com.diego.matesanz.arcaneum.ui.navigation.Explore
import com.diego.matesanz.arcaneum.ui.navigation.Home
import com.diego.matesanz.arcaneum.ui.screens.bookDetail.view.BookDetailScreen
import com.diego.matesanz.arcaneum.ui.screens.bookDetail.viewModel.BookDetailViewModel
import com.diego.matesanz.arcaneum.ui.screens.camera.view.CameraScreen
import com.diego.matesanz.arcaneum.ui.screens.camera.viewModel.CameraViewModel
import com.diego.matesanz.arcaneum.ui.screens.home.view.HomeScreen
import com.diego.matesanz.arcaneum.ui.screens.home.viewModel.HomeViewModel
import com.diego.matesanz.arcaneum.usecases.FindBookByIdUseCase
import com.diego.matesanz.arcaneum.usecases.FindBookByIsbnUseCase
import com.diego.matesanz.arcaneum.usecases.FindBooksBySearchTextUseCase
import com.diego.matesanz.arcaneum.usecases.GetShelvesUseCase
import com.diego.matesanz.arcaneum.usecases.ToggleBookShelfUseCase

fun NavGraphBuilder.exploreTab(
    navController: NavHostController,
    shelvesRepository: ShelvesRepository,
    booksRepository: BooksRepository,
) {
    navigation<Explore>(startDestination = Home) {
        composable<Home> {
            HomeScreen(
                onBookClick = { book -> navController.navigate(BookDetail(book.bookId)) },
                onCamClick = { navController.navigate(Camera) },
                viewModel = viewModel {
                    HomeViewModel(
                        findBooksBySearchTextUseCase = FindBooksBySearchTextUseCase(booksRepository),
                        getShelvesUseCase = GetShelvesUseCase(shelvesRepository),
                        toggleBookShelfUseCase = ToggleBookShelfUseCase(booksRepository)
                    )
                },
            )
        }
        composable<BookDetail> { backStackEntry ->
            val bookDetail: BookDetail = backStackEntry.toRoute()
            BookDetailScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel {
                    BookDetailViewModel(
                        bookId = bookDetail.bookId,
                        findBookByIdUseCase = FindBookByIdUseCase(booksRepository),
                        getShelvesUseCase = GetShelvesUseCase(shelvesRepository),
                        toggleBookShelfUseCase = ToggleBookShelfUseCase(booksRepository),
                    )
                },
            )
        }
        composable<Camera> {
            CameraScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navController.navigate(BookDetail(book.bookId)) },
                viewModel = viewModel { CameraViewModel(FindBookByIsbnUseCase(booksRepository)) },
            )
        }
    }
}
