package com.diego.matesanz.arcaneum.ui.common.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.diego.matesanz.arcaneum.data.Result

@Composable
fun <T> ResultScaffold(
    state: Result<T>,
    modifier: Modifier = Modifier,
    loading: @Composable () -> Unit = {},
    error: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    contentWindowInsets: WindowInsets = WindowInsets.safeDrawing,
    content: @Composable (PaddingValues, T) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        contentWindowInsets = contentWindowInsets,
    ) { padding ->
        when (state) {
            is Result.Error -> error()
            is Result.Loading -> loading()
            is Result.Success -> content(padding, state.data)
        }
    }
}
