package com.diego.matesanz.arcaneum.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.diego.matesanz.arcaneum.ui.theme.ArcaneumTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    ArcaneumTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content,
        )
    }
}
