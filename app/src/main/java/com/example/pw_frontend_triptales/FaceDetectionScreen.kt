package com.example.pw_frontend_triptales

import android.graphics.Rect
import android.util.Log
import androidx.annotation.OptIn
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
import com.google.mlkit.vision.face.*
import java.util.concurrent.Executors

data class FaceData(
    val boundingBox: Rect,
    val smilingProb: Float?,
    val leftEyeOpenProb: Float?,
    val rightEyeOpenProb: Float?
)

@OptIn(ExperimentalGetImage::class)
@Composable
fun FaceDetectionScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val faceResults = remember { mutableStateListOf<FaceData>() }

    val preview = remember { Preview.Builder().build() }
    val imageAnalysis = remember {
        ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
    }

    val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .enableTracking()
        .build()

    val detector = FaceDetection.getClient(options)
    val executor = remember { Executors.newSingleThreadExecutor() }

    imageAnalysis.setAnalyzer(executor) { imageProxy ->
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            detector.process(inputImage)
                .addOnSuccessListener { faces ->
                    faceResults.clear()
                    faceResults.addAll(faces.map {
                        FaceData(
                            boundingBox = it.boundingBox,
                            smilingProb = it.smilingProbability,
                            leftEyeOpenProb = it.leftEyeOpenProbability,
                            rightEyeOpenProb = it.rightEyeOpenProbability
                        )
                    })
                }
                .addOnFailureListener { Log.e("FaceDetect", "Errore MLKit", it) }
                .addOnCompleteListener { imageProxy.close() }
        } else {
            imageProxy.close()
        }
    }

    LaunchedEffect(true) {
        val cameraProvider = cameraProviderFuture.get()
        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        try {
            cameraProvider.unbindAll()
            preview.setSurfaceProvider(previewView.surfaceProvider)
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
        } catch (e: Exception) {
            Log.e("CameraX", "Errore bind camera", e)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
        )

        FaceOverlay(
            faces = faceResults.toList(),
            previewView = previewView
        )
    }
}