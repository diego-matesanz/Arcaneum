package com.diego.matesanz.arcaneum.ui.navigation.bottombar.tabs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.ShelvesRepository

fun NavGraphBuilder.topLevelTabs(
    navController: NavHostController,
    shelvesRepository: ShelvesRepository,
    booksRepository: BooksRepository,
) {
    exploreTab(
        navController = navController,
        shelvesRepository = shelvesRepository,
        booksRepository = booksRepository,
    )
    bookmarksTab(
        navController = navController,
        shelvesRepository = shelvesRepository,
        booksRepository = booksRepository,
    )
}
