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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.diego.matesanz.arcaneum.R

@Composable
fun CustomAsyncImage(
    model: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onDominantColor: ((Int) -> Unit)? = null,
) {
    Box(modifier = modifier) {
        var imageLoading by remember { mutableStateOf(true) }
        val modelRequest =
            ImageRequest.Builder(LocalContext.current).data(model).allowHardware(false).build()
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.small),
            model = modelRequest,
            contentDescription = contentDescription,
            error = painterResource(id = R.drawable.ic_book_placeholder),
            onSuccess = { painterState ->
                onDominantColor?.let {
                    Palette.Builder(painterState.result.drawable.toBitmap()).generate { palette ->
                        palette?.dominantSwatch?.rgb?.let { color ->
                            it(color)
                        }
                    }
                }
                imageLoading = false
            },
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
