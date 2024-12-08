package com.diego.matesanz.arcaneum.ui.screens.detail.stateHolder

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.diego.matesanz.arcaneum.ui.common.stateHolders.BasicBooksScreenState

@OptIn(ExperimentalMaterial3Api::class)
class DetailState(
    scrollBehavior: TopAppBarScrollBehavior,
    snackbarHostState: SnackbarHostState,
) : BasicBooksScreenState(scrollBehavior, snackbarHostState)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberDetailState(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
): DetailState {
    return remember(scrollBehavior, snackbarHostState) {
        DetailState(
            scrollBehavior = scrollBehavior,
            snackbarHostState = snackbarHostState,
        )
    }
}
