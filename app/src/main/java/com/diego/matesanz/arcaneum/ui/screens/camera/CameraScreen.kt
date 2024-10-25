package com.diego.matesanz.arcaneum.ui.screens.camera

import android.Manifest
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.diego.matesanz.arcaneum.R
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.ui.common.Loader
import com.diego.matesanz.arcaneum.ui.common.PermissionRequestEffect
import com.diego.matesanz.arcaneum.ui.screens.Screen
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    onBack: () -> Unit,
    onBookClick: (Book) -> Unit,
    viewModel: CameraViewModel = viewModel(),
) {
    val state = viewModel.state
    var permissionGranted by remember { mutableStateOf(false) }

    PermissionRequestEffect(permission = Manifest.permission.CAMERA) { permissionGranted = it }

    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(R.string.go_back),
                                tint = MaterialTheme.colorScheme.surface,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            contentWindowInsets = WindowInsets.safeGestures,
        ) { padding ->
            if (permissionGranted) {
                ScanningScreen(
                    book = state.book,
                    isLoading = state.isLoading,
                    isError = state.isError,
                    onBookScanned = viewModel::fetchBookByIsbn,
                    onBookClick = onBookClick,
                    padding = padding,
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(R.string.need_camera_permission_to_scan),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Composable
private fun ScanningScreen(
    book: Book?,
    isLoading: Boolean,
    isError: Boolean,
    onBookScanned: (String) -> Unit,
    onBookClick: (Book) -> Unit,
    padding: PaddingValues,
) {
    var scanFlag by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }
    var lastReadBarcode by remember { mutableStateOf<String?>(null) }
    Box {
        AndroidView(
            factory = { context ->
                val preview = CompoundBarcodeView(context)
                preview.setStatusText("")
                preview.apply {
                    val capture = CaptureManager(context as Activity, this)
                    capture.initializeFromIntent(context.intent, null)
                    capture.decode()
                    this.decodeContinuous { result ->
                        if (scanFlag) {
                            return@decodeContinuous
                        }
                        scanFlag = true
                        result.text?.let {
                            lastReadBarcode = result.text
                            scanFlag = true
                            showResult = true
                            onBookScanned(it)
                        }
                    }
                    this.resume()
                }
            },
            modifier = Modifier.fillMaxSize(),
        )

        AnimatedVisibility(
            visible = showResult,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            when {
                isLoading -> Loader()
                isError -> ErrorResult(padding)
                else -> {
                    book?.let {
                        BookResult(
                            book = it,
                            onBookClick = {
                                showResult = false
                                onBookClick(book)
                            },
                            padding = padding,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorResult(
    padding: PaddingValues,
) {
    Surface(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp,
            )
            .padding(padding)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(90.dp),
                painter = painterResource(R.drawable.ic_error),
                contentDescription = null,
            )
            Text(
                text = stringResource(R.string.error_scanning_book),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun BookResult(
    book: Book,
    onBookClick: () -> Unit,
    padding: PaddingValues,
) {
    Surface(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp,
            )
            .padding(padding)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .clickable(onClick = onBookClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(90.dp)
                    .aspectRatio(1 / 1.5F)
                    .clip(MaterialTheme.shapes.small),
                model = book.coverImage,
                contentDescription = book.title,
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                var authorsText = StringBuilder()
                book.authors.forEachIndexed { index, author ->
                    authorsText.append(author)
                    if (index < book.authors.lastIndex) {
                        authorsText.append(", ")
                    }
                }
                Text(
                    text = authorsText.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}