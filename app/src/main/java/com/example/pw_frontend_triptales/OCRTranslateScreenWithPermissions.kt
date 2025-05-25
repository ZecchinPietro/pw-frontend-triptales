package com.example.pw_frontend_triptales

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OCRTranslateScreenWithPermissions() {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    when {
        cameraPermissionState.status.isGranted -> {
            OCRTranslateScreen()
        }
        cameraPermissionState.status.shouldShowRationale -> {
            Text("Hai negato il permesso per la fotocamera. Per usare l'OCR, devi concederlo.")
        }
        else -> {
            Text("Richiesta permesso per accedere alla fotocamera in corso...")
        }
    }
}
