package com.diego.matesanz.arcaneum.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.ui.theme.ArcaneumTheme

@Composable
fun Screen(
    contentDescription: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    ArcaneumTheme {
        Surface(
            modifier = modifier
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
