package com.example.pw_frontend_triptales

import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.util.concurrent.Executors

@Composable
fun ImageLabelingScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val labelResults = remember { mutableStateListOf<String>() }

    val preview = remember { Preview.Builder().build() }
    val imageAnalysis = remember {
        ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
    }

    val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
    val executor = remember { Executors.newSingleThreadExecutor() }

    imageAnalysis.setAnalyzer(executor) { imageProxy ->
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            labeler.process(inputImage)
                .addOnSuccessListener { labels ->
                    labelResults.clear()
                    labelResults.addAll(labels.map { "${it.text} (${(it.confidence * 100).toInt()}%)" })
                }
                .addOnFailureListener { Log.e("ImageLabeling", "Errore MLKit", it) }
                .addOnCompleteListener { imageProxy.close() }
        } else {
            imageProxy.close()
        }
    }

    LaunchedEffect(true) {
        val cameraProvider = cameraProviderFuture.get()
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        try {
            cameraProvider.unbindAll()
            preview.setSurfaceProvider(previewView.surfaceProvider)
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
        } catch (e: Exception) {
            Log.e("CameraX", "Errore bind camera", e)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f / 4f)) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
        }

        Divider(thickness = 2.dp)

        Text("Etichette rilevate:", modifier = Modifier.padding(8.dp))
        labelResults.forEach { label ->
            Text(
                text = label,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}
