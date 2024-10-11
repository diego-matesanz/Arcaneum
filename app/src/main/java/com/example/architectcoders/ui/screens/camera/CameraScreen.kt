package com.example.architectcoders.ui.screens.camera

import android.Manifest
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.architectcoders.Book
import com.example.architectcoders.R
import com.example.architectcoders.books
import com.example.architectcoders.ui.common.PermissionRequestEffect
import com.example.architectcoders.ui.screens.Screen
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView

@Composable
fun CameraScreen(
    onBack: () -> Unit,
    onBookClick: (Book) -> Unit,
) {
    var cameraPermissionGranted by remember { mutableStateOf(false) }

    PermissionRequestEffect(permission = Manifest.permission.CAMERA) { isGranted ->
        cameraPermissionGranted = isGranted
        if (isGranted) {
            println("Camera permission granted")
        } else {
            println("Camera permission denied")
        }
    }

    Screen {
        Scaffold(
            contentWindowInsets = WindowInsets.safeGestures,
        ) { padding ->
            if (cameraPermissionGranted) {
                ScanningScreen(
                    onBack = onBack,
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
    onBack: () -> Unit,
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
            modifier = Modifier
                .fillMaxSize()
        )
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(padding)
                .padding(start = 16.dp)
                .align(Alignment.TopStart)
                .background(colorResource(id = R.color.white_alpha_50), shape = RoundedCornerShape(100))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(R.string.go_back),
            )
        }
        if (showResult) {
            lastReadBarcode?.let {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .clickable(onClick = {
                            showResult = false
                            onBookClick(books.first())
                        })
                ) {
                    Text(
                        text = lastReadBarcode ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}