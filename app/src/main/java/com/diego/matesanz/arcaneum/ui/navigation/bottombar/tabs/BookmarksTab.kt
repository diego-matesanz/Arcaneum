package com.diego.matesanz.arcaneum.ui.navigation.bottombar.tabs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.diego.matesanz.arcaneum.ui.navigation.BookDetail
import com.diego.matesanz.arcaneum.ui.navigation.Bookmarks
import com.diego.matesanz.arcaneum.ui.navigation.ShelfDetail
import com.diego.matesanz.arcaneum.ui.navigation.Shelves
import com.diego.matesanz.arcaneum.ui.screens.bookDetail.view.BookDetailScreen
import com.diego.matesanz.arcaneum.ui.screens.shelfDetail.view.ShelfDetailScreen
import com.diego.matesanz.arcaneum.ui.screens.shelves.view.ShelvesScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.bookmarksTab(navController: NavHostController) {
    navigation<Bookmarks>(startDestination = Shelves) {
        composable<Shelves> {
            ShelvesScreen(
                onShelfClick = { shelf ->
                    navController.navigate(ShelfDetail(shelf.shelfId))
                },
                viewModel = koinViewModel(),
            )
        }
        composable<ShelfDetail> { backStackEntry ->
            val shelfDetail: ShelfDetail = backStackEntry.toRoute()
            ShelfDetailScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navController.navigate(BookDetail(book.bookId)) },
                viewModel = koinViewModel(parameters = { parametersOf(shelfDetail.shelfId) }),
            )
        }
        composable<BookDetail> { backStackEntry ->
            val bookDetail: BookDetail = backStackEntry.toRoute()
            BookDetailScreen(
                onBack = { navController.popBackStack() },
                viewModel = koinViewModel(parameters = { parametersOf(bookDetail.bookId) }),
            )
        }
    }
}
