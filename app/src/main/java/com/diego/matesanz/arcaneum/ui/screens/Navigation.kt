package com.diego.matesanz.arcaneum.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.diego.matesanz.arcaneum.App
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import com.diego.matesanz.arcaneum.data.datasource.BooksLocalDataSource
import com.diego.matesanz.arcaneum.data.datasource.BooksRemoteDataSource
import com.diego.matesanz.arcaneum.data.datasource.ShelvesLocalDataSource
import com.diego.matesanz.arcaneum.ui.screens.bookDetail.view.BookDetailScreen
import com.diego.matesanz.arcaneum.ui.screens.bookDetail.viewModel.BookDetailViewModel
import com.diego.matesanz.arcaneum.ui.screens.camera.view.CameraScreen
import com.diego.matesanz.arcaneum.ui.screens.camera.viewModel.CameraViewModel
import com.diego.matesanz.arcaneum.ui.screens.home.view.HomeScreen
import com.diego.matesanz.arcaneum.ui.screens.home.viewModel.HomeViewModel
import com.diego.matesanz.arcaneum.ui.screens.shelfDetail.view.ShelfDetailScreen
import com.diego.matesanz.arcaneum.ui.screens.shelfDetail.viewModel.ShelfDetailViewModel
import com.diego.matesanz.arcaneum.ui.screens.shelves.view.ShelvesScreen
import com.diego.matesanz.arcaneum.ui.screens.shelves.viewModel.ShelvesViewModel
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {
    val app = LocalContext.current.applicationContext as App
    val navController = rememberNavController()
    val shelvesRepository = ShelvesRepository(ShelvesLocalDataSource(app.db.shelvesDao()))
    val booksRepository = BooksRepository(
        remoteDataSource = BooksRemoteDataSource(),
        localDataSource = BooksLocalDataSource(app.db.booksDao()),
        shelvesRepository = shelvesRepository,
    )

    val topLevelRoutes = listOf(
        TopLevelRoute("Explore", Explore, Icons.Default.Search),
        TopLevelRoute("Bookmarks", Bookmarks, Icons.Default.Bookmarks)
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry.value?.destination
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                topLevelRoutes.forEach { topLevelRoute ->
                    BottomNavigationItem(
                        icon = topLevelRoute.icon,
                        name = topLevelRoute.name,
                        selected = currentDestination?.hierarchy?.any {
                            it.route == topLevelRoute.route::class.toString()
                        } == true,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            shelvesRepository = shelvesRepository,
            booksRepository = booksRepository,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun MainNavHost(
    navController: NavHostController,
    shelvesRepository: ShelvesRepository,
    booksRepository: BooksRepository,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Explore,
        modifier = modifier,
    ) {
        mainTabs(
            navController = navController,
            shelvesRepository = shelvesRepository,
            booksRepository = booksRepository,
        )
    }
}

private fun NavGraphBuilder.mainTabs(
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

private fun NavGraphBuilder.exploreTab(
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
                        booksRepository = booksRepository,
                        shelvesRepository = shelvesRepository,
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
                        booksRepository = booksRepository,
                        shelvesRepository = shelvesRepository,
                    )
                },
            )
        }
        composable<Camera> {
            CameraScreen(
                onBack = { navController.popBackStack() },
                onBookClick = { book -> navController.navigate(BookDetail(book.bookId)) },
                viewModel = viewModel { CameraViewModel(booksRepository) },
            )
        }
    }
}

private fun NavGraphBuilder.bookmarksTab(
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
                        shelvesRepository = shelvesRepository,
                        booksRepository = booksRepository,
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
                        booksRepository = booksRepository,
                        shelvesRepository = shelvesRepository,
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
                        booksRepository = booksRepository,
                        shelvesRepository = shelvesRepository,
                    )
                },
            )
        }
    }
}

@Composable
fun BottomNavigationItem(
    name: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(
                onClick = onClick,
                role = Role.Tab,
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = name,
            tint = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onTertiaryContainer
        )
        Text(
            text = name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onTertiaryContainer,
        )
    }
}

private data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

/**
 * Top level destinations
 */
@Serializable
object Explore

@Serializable
object Bookmarks


/**
 * Explore Screens
 */
@Serializable
object Home

@Serializable
data class BookDetail(val bookId: String)

@Serializable
object Camera


/**
 * Bookmarks screens
 */
@Serializable
object Shelves

@Serializable
data class ShelfDetail(val shelfId: Int)
