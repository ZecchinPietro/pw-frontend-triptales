package com.example.pw_frontend_triptales

import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun FaceOverlay(
    faces: List<FaceData>,
    previewView: PreviewView
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val previewWidth = previewView.width.toFloat()
        val previewHeight = previewView.height.toFloat()

        if (previewWidth == 0f || previewHeight == 0f) return@Canvas

        val scaleX = canvasWidth / previewWidth
        val scaleY = canvasHeight / previewHeight

        drawIntoCanvas { canvas ->
            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.GREEN
                style = android.graphics.Paint.Style.STROKE
                strokeWidth = 4f
                textSize = 36f
            }

            for (face in faces) {
                val box = face.boundingBox

                val mirroredLeft = previewWidth - box.right
                val mirroredRight = previewWidth - box.left

                val left = mirroredLeft * scaleX
                val top = box.top * scaleY
                val right = mirroredRight * scaleX
                val bottom = box.bottom * scaleY

                canvas.nativeCanvas.drawRect(left, top, right, bottom, paint)

                val smile = face.smilingProb?.let { "Sorriso: ${(it * 100).toInt()}%" } ?: ""
                val eyeLeft = face.leftEyeOpenProb?.let { "Occhio SX: ${(it * 100).toInt()}%" } ?: ""
                val eyeRight = face.rightEyeOpenProb?.let { "DX: ${(it * 100).toInt()}%" } ?: ""

                val info = listOf(smile, eyeLeft, eyeRight).filter { it.isNotEmpty() }.joinToString(" | ")

                canvas.nativeCanvas.drawText(
                    info,
                    left,
                    top - 10f,
                    paint
                )
            }
        }
    }
}
