package com.diego.matesanz.arcaneum.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.diego.matesanz.arcaneum.R

@Composable
fun CustomAsyncImage(
    model: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        var imageLoading by remember { mutableStateOf(true) }
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.small),
            model = model,
            contentDescription = contentDescription,
            error = painterResource(id = R.drawable.ic_book_placeholder),
            onSuccess = { imageLoading = false },
            onError = { imageLoading = false },
        )
        if (imageLoading) {
            LoadingSkeleton(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small),
            )
        }
    }
}
