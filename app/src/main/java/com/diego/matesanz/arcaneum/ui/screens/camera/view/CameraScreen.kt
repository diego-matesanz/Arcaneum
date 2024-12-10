package com.diego.matesanz.arcaneum.ui.screens.camera.view

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diego.matesanz.arcaneum.R
import com.diego.matesanz.arcaneum.constants.BOOK_ASPECT_RATIO
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.ui.common.components.CustomAsyncImage
import com.diego.matesanz.arcaneum.ui.screens.Screen
import com.diego.matesanz.arcaneum.ui.screens.camera.viewModel.CameraAction
import com.diego.matesanz.arcaneum.ui.screens.camera.viewModel.CameraViewModel
import com.diego.matesanz.arcaneum.ui.screens.camera.stateHolder.rememberCameraState
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView

@Composable
fun CameraScreen(
    onBack: () -> Unit,
    onBookClick: (Book) -> Unit,
    viewModel: CameraViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val cameraState = rememberCameraState()

    cameraState.AskCameraPermission { viewModel.onAction(CameraAction.PermissionResult(it)) }

    Screen(
        contentDescription = stringResource(R.string.camera_screen_accessibility_description),
    ) {
        Scaffold(
            topBar = { CameraTopBar(onBack = onBack) },
            contentWindowInsets = WindowInsets.safeGestures,
        ) { padding ->
            CameraContent(
                state = state,
                onBookScanned = { viewModel.onAction(CameraAction.BookScanned(it)) },
                onBookClick = onBookClick,
                modifier = Modifier.padding(padding),
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CameraTopBar(onBack: () -> Unit) {
    TopAppBar(
        title = { Text("") },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.go_back_action_accessibility_description),
                    tint = MaterialTheme.colorScheme.surface,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
private fun CameraContent(
    state: CameraViewModel.UiState,
    onBookScanned: (String) -> Unit,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.permissionGranted) {
        ScanningScreen(
            book = state.book,
            isLoading = state.isLoading,
            isError = state.isError,
            onBookScanned = onBookScanned,
            onBookClick = onBookClick,
            modifier = modifier,
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

@Composable
private fun ScanningScreen(
    book: Book?,
    isLoading: Boolean,
    isError: Boolean,
    onBookScanned: (String) -> Unit,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
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
                isLoading -> BookResultLoader(modifier = modifier)
                isError -> BookResultError(modifier = modifier)
                else -> {
                    book?.let {
                        BookResult(
                            book = it,
                            onBookClick = {
                                showResult = false
                                onBookClick(book)
                            },
                            modifier = modifier,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookResult(
    book: Book,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp,
            )
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
            CustomAsyncImage(
                model = book.coverImage,
                contentDescription = stringResource(
                    R.string.book_cover_content_accessibility_description,
                    book.title
                ),
                modifier = Modifier
                    .height(90.dp)
                    .aspectRatio(BOOK_ASPECT_RATIO),
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
