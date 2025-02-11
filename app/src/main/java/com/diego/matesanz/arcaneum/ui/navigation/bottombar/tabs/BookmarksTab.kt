package com.diego.matesanz.arcaneum.ui.navigation.bottombar.tabs

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.data.repositories.ShelvesRepository
import com.diego.matesanz.arcaneum.ui.navigation.BookDetail
import com.diego.matesanz.arcaneum.ui.navigation.Bookmarks
import com.diego.matesanz.arcaneum.ui.navigation.ShelfDetail
import com.diego.matesanz.arcaneum.ui.navigation.Shelves
import com.diego.matesanz.arcaneum.ui.screens.bookDetail.view.BookDetailScreen
import com.diego.matesanz.arcaneum.ui.screens.bookDetail.viewModel.BookDetailViewModel
import com.diego.matesanz.arcaneum.ui.screens.shelfDetail.view.ShelfDetailScreen
import com.diego.matesanz.arcaneum.ui.screens.shelfDetail.viewModel.ShelfDetailViewModel
import com.diego.matesanz.arcaneum.ui.screens.shelves.view.ShelvesScreen
import com.diego.matesanz.arcaneum.ui.screens.shelves.viewModel.ShelvesViewModel
import com.diego.matesanz.arcaneum.usecases.CreateShelfUseCase
import com.diego.matesanz.arcaneum.usecases.FindBookByIdUseCase
import com.diego.matesanz.arcaneum.usecases.FindBooksByShelfIdUseCase
import com.diego.matesanz.arcaneum.usecases.GetBooksByShelfUseCase
import com.diego.matesanz.arcaneum.usecases.GetShelvesUseCase
import com.diego.matesanz.arcaneum.usecases.RemoveShelfUseCase
import com.diego.matesanz.arcaneum.usecases.ToggleBookShelfUseCase

fun NavGraphBuilder.bookmarksTab(
    navController: NavHostController,
    shelvesRepository: ShelvesRepository,
    booksRepository: BooksRepository,
) {
    navigation<Bookmarks>(startDestination = Shelves) {
        composable<Shelves> {
            ShelvesScreen(
                onShelfClick = { shelf ->
                    navController.navigate(ShelfDetail(shelf.shelfId))
                },
                viewModel = viewModel {
                    ShelvesViewModel(
                        getBooksByShelfUseCase = GetBooksByShelfUseCase(booksRepository),
                        createShelfUseCase = CreateShelfUseCase(shelvesRepository),
                        removeShelfUseCase = RemoveShelfUseCase(shelvesRepository),
                    )
                },
            )
        }
        composable<ShelfDetail> { backStackEntry ->
            val shelfDetail: ShelfDetail = backStackEntry.toRoute()
            ShelfDetailScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navController.navigate(BookDetail(book.bookId)) },
                viewModel = viewModel {
                    ShelfDetailViewModel(
                        shelfId = shelfDetail.shelfId,
                        findBooksByShelfIdUseCase = FindBooksByShelfIdUseCase(booksRepository),
                        getShelvesUseCase = GetShelvesUseCase(shelvesRepository),
                        toggleBookShelfUseCase = ToggleBookShelfUseCase(booksRepository),
                    )
                }
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
    }
}
