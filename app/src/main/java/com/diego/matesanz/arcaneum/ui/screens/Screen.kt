package com.diego.matesanz.arcaneum.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import com.diego.matesanz.arcaneum.ui.theme.ArcaneumTheme

@Composable
fun Screen(
    contentDescription: String,
    content: @Composable () -> Unit,
) {
    ArcaneumTheme {
        Surface(
            modifier = Modifier
                .semantics(mergeDescendants = false, properties = {
                    this.contentDescription = contentDescription
                    isTraversalGroup = true
                })
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content,
        )
    }
}
