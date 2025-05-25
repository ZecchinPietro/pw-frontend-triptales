package com.example.pw_frontend_triptales

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File

@Composable
fun OCRTranslateScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }

    var recognizedText by remember { mutableStateOf("") }
    var translatedText by remember { mutableStateOf("") }
    var captureRequested by remember { mutableStateOf(false) }

    val imageCapture = remember { ImageCapture.Builder().build() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Anteprima fotocamera
        AndroidView(
            factory = { previewView },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 16.dp)
        )

        LaunchedEffect(true) {
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Log.e("CameraX", "Errore binding camera", e)
            }
        }

        // Bottone
        Button(
            onClick = { captureRequested = true },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        ) {
            Text("Scatta Foto & OCR")
        }

        // Area scrollabile con il testo riconosciuto e tradotto
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (recognizedText.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Testo Riconosciuto:", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(recognizedText)
                    }
                }
            }

            if (translatedText.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Traduzione:", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(translatedText)
                    }
                }
            }
        }
    }

    // Capture & process immagine
    if (captureRequested) {
        val file = File(context.cacheDir, "ocr_photo.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                    recognizeTextFromBitmap(bitmap) { testo ->
                        recognizedText = testo
                        translateText(testo, TranslateLanguage.ENGLISH, TranslateLanguage.ITALIAN) {
                            translatedText = it
                        }
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("OCR", "Errore cattura: ${exception.message}")
                }
            }
        )
        captureRequested = false
    }
}



fun recognizeTextFromBitmap(bitmap: Bitmap, onResult: (String) -> Unit) {
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val image = InputImage.fromBitmap(bitmap, 0)

    recognizer.process(image)
        .addOnSuccessListener { visionText ->
            onResult(visionText.text)
        }
        .addOnFailureListener {
            Log.e("MLKit", "Errore OCR", it)
            onResult("")
        }
}


fun translateText(
    sourceText: String,
    sourceLang: String = TranslateLanguage.ENGLISH,
    targetLang: String = TranslateLanguage.ITALIAN,
    onTranslated: (String) -> Unit
) {
    val options = TranslatorOptions.Builder()
        .setSourceLanguage(sourceLang)
        .setTargetLanguage(targetLang)
        .build()

    val translator = Translation.getClient(options)

    translator.downloadModelIfNeeded()
        .addOnSuccessListener {
            translator.translate(sourceText)
                .addOnSuccessListener(onTranslated)
                .addOnFailureListener {
                    Log.e("MLKit", "Errore traduzione", it)
                    onTranslated("")
                }
        }
        .addOnFailureListener {
            Log.e("MLKit", "Errore download modello", it)
            onTranslated("")
        }
}

