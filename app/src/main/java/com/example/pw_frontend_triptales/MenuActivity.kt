package com.example.pw_frontend_triptales

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.example.pw_frontend_triptales.ui.theme.TripTalesTheme

class MenuActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token", "debug-token")

        setContent {
            val context = LocalContext.current
            var isDarkTheme by remember {
                mutableStateOf(
                    sharedPreferences.getBoolean("is_dark_theme", false)
                )
            }

            TripTalesTheme(useDarkTheme = isDarkTheme) {
                if (accessToken != null) {
                    val navController = rememberNavController()

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text("TripTales", style = MaterialTheme.typography.headlineMedium)
                                },
                                actions = {
                                    IconButton(onClick = {
                                        navController.navigate("settings")
                                    }) {
                                        Icon(Icons.Default.Settings, contentDescription = "Impostazioni")
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                            if (currentRoute !in listOf("settings")) {
                                BottomNavigationBar(navController)
                            }
                        }
                    ) { padding ->
                        NavHost(
                            navController = navController,
                            startDestination = BottomNavItem.Menu.route,
                            modifier = Modifier.padding(padding)
                        ) {
                            composable(BottomNavItem.Menu.route) {
                                MainMenuScreen(accessToken) {
                                    with(sharedPreferences.edit()) {
                                        remove("access_token")
                                        remove("refresh_token")
                                        apply()
                                    }
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    context.startActivity(intent)
                                }
                            }
                            composable(BottomNavItem.FaceDetection.route) {
                                FaceDetectionScreen()
                            }
                            composable(BottomNavItem.ImageLabeling.route) {
                                ImageLabelingScreen()
                            }
                            composable(BottomNavItem.OCR.route) {
                                OCRTranslateScreenWithPermissions()
                            }
                            composable("settings") {
                                SettingsScreen(
                                    isDarkTheme = isDarkTheme,
                                    onThemeToggle = {
                                        isDarkTheme = !isDarkTheme
                                        sharedPreferences.edit().putBoolean("is_dark_theme", isDarkTheme).apply()
                                    },
                                    onLogout = {
                                        with(sharedPreferences.edit()) {
                                            remove("access_token")
                                            remove("refresh_token")
                                            apply()
                                        }
                                        val intent = Intent(context, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        context.startActivity(intent)
                                    }
                                )
                            }
                        }
                    }
                } else {
                    Text("Accesso non valido. Effettua il login.")
                }
            }
        }
    }
}
