package com.diego.matesanz.arcaneum.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable


val topLevelRoutes = listOf(
    TopLevelRoute("Explore", Explore, Icons.Default.Search),
    TopLevelRoute("Bookmarks", Bookmarks, Icons.Default.Bookmarks)
)

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

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
