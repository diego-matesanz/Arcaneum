package com.diego.matesanz.arcaneum.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.diego.matesanz.arcaneum.App
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import com.diego.matesanz.arcaneum.framework.remote.BooksClient
import com.diego.matesanz.arcaneum.framework.database.datasource.BooksRoomDataSource
import com.diego.matesanz.arcaneum.framework.database.datasource.ShelvesRoomDataSource
import com.diego.matesanz.arcaneum.framework.remote.datasources.BooksServerDataSource
import com.diego.matesanz.arcaneum.ui.navigation.bottombar.BottomBar
import com.diego.matesanz.arcaneum.ui.navigation.bottombar.tabs.topLevelTabs

@Composable
fun Navigation() {
    val app = LocalContext.current.applicationContext as App
    val navController = rememberNavController()
    val shelvesRepository = ShelvesRepository(ShelvesRoomDataSource(app.db.shelvesDao()))
    val booksRepository = BooksRepository(
        remoteDataSource = BooksServerDataSource(BooksClient.instance),
        localDataSource = BooksRoomDataSource(app.db.booksDao()),
        shelvesRepository = shelvesRepository,
    )

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Explore,
            modifier = Modifier.padding(innerPadding),
        ) {
            topLevelTabs(
                navController = navController,
                shelvesRepository = shelvesRepository,
                booksRepository = booksRepository,
            )
        }
    }
}
