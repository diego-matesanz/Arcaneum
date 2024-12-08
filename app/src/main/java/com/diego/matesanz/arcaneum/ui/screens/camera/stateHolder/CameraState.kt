package com.diego.matesanz.arcaneum.ui.screens.camera.stateHolder

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.diego.matesanz.arcaneum.ui.common.components.PermissionRequestEffect

class CameraState {

    @Composable
    fun AskCameraPermission(onPermissionResult: (Boolean) -> Unit) {
        PermissionRequestEffect(permission = Manifest.permission.CAMERA) { onPermissionResult(it) }
    }
}

@Composable
fun rememberCameraState(): CameraState {
    return remember { CameraState() }
}
