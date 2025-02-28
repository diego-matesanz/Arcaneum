package com.diego.matesanz.arcaneum.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.diego.matesanz.arcaneum.ui.navigation.bottombar.BottomBar
import com.diego.matesanz.arcaneum.ui.navigation.bottombar.tabs.bookmarksTab
import com.diego.matesanz.arcaneum.ui.navigation.bottombar.tabs.exploreTab

@Composable
fun Navigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Explore,
            modifier = Modifier.padding(innerPadding),
        ) {
            topLevelTabs(navController = navController)
        }
    }
}

private fun NavGraphBuilder.topLevelTabs(navController: NavHostController) {
    exploreTab(navController = navController)
    bookmarksTab(navController = navController)
}
