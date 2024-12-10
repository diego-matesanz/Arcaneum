package com.diego.matesanz.arcaneum.ui.common.stateHolders

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
abstract class BasicBooksScreenState(
    val scrollBehavior: TopAppBarScrollBehavior,
    val snackbarHostState: SnackbarHostState,
) {

    @Composable
    fun ShowMessageEffect(message: String?, onShown: () -> Unit) {
        LaunchedEffect(message) {
            message?.let { message ->
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(message = message)
                onShown()
            }
        }
    }
}
