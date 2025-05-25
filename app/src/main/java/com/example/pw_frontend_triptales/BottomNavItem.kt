package com.example.pw_frontend_triptales

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

object BottomNavItem {
    val Menu = NavItem("menu", Icons.Default.Home, "Home")
    val FaceDetection = NavItem("face", Icons.Default.Face, "Facciale")
    val ImageLabeling = NavItem("image", Icons.Default.AccountBox, "Immagini")
    val OCR = NavItem("ocr", Icons.Default.Add, "Traduttore")

}

data class NavItem(val route: String, val icon: ImageVector, val label: String)


