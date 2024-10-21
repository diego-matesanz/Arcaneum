package com.example.architectcoders.ui.screens.camera

import android.Manifest
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
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
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.AsyncImage
import com.example.architectcoders.Book
import com.example.architectcoders.R
import com.example.architectcoders.books
import com.example.architectcoders.ui.common.PermissionRequestEffect
import com.example.architectcoders.ui.screens.Screen
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    onBack: () -> Unit,
    onBookClick: (Book) -> Unit,
) {
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
                        result.text?.let { barCodeOrQr ->
                            lastReadBarcode = result.text
                            scanFlag = true
                            showResult = true
                        }
                    }
                    this.resume()
                }
            },
            modifier = Modifier.fillMaxSize(),
        )
        LaunchedEffect(true) {
            withContext(Dispatchers.Default) {
                Thread.sleep(3000)
                showResult = true
            }
        }
        AnimatedVisibility(
            visible = showResult,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BookResult(
                book = books.first(),
                onBookClick = {
                    showResult = false
                    onBookClick(books.first())
                },
                padding = padding,
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
            .clip(MaterialTheme.shapes.medium)
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
                    .width(60.dp)
                    .aspectRatio(2 / 3F)
                    .clip(MaterialTheme.shapes.medium),
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
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}