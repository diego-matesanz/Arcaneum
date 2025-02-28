package com.diego.matesanz.arcaneum.ui.navigation.bottombar.tabs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.diego.matesanz.arcaneum.ui.navigation.BookDetail
import com.diego.matesanz.arcaneum.ui.navigation.Bookmarks
import com.diego.matesanz.arcaneum.ui.navigation.ShelfDetail
import com.diego.matesanz.arcaneum.ui.navigation.Shelves
import com.diego.matesanz.arcaneum.ui.screens.bookDetail.view.BookDetailScreen
import com.diego.matesanz.arcaneum.ui.screens.shelfDetail.view.ShelfDetailScreen
import com.diego.matesanz.arcaneum.ui.screens.shelves.view.ShelvesScreen

fun NavGraphBuilder.bookmarksTab(navController: NavHostController) {
    navigation<Bookmarks>(startDestination = Shelves) {
        composable<Shelves> {
            ShelvesScreen(
                onShelfClick = { shelf ->
                    navController.navigate(ShelfDetail(shelf.shelfId))
                },
            )
        }
        composable<ShelfDetail> {
            ShelfDetailScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navController.navigate(BookDetail(book.bookId)) },
            )
        }
        composable<BookDetail> {
            BookDetailScreen(
                onBack = { navController.popBackStack() },
            )
        }
    }
}
