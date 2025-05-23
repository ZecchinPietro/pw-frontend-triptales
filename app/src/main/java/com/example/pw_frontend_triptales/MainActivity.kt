package com.example.pw_frontend_triptales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TripTalesApp()
        }
    }
}

@Composable
fun TripTalesApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "access") {
        composable("access") { AccessScreen(navController) }
        composable("main") { MainMenuScreen(navController) }
        composable("login") { /* LoginScreen() */ }
        composable("register") { /* RegistrationScreen() */ }
        composable("groups") { /* GroupManagementScreen() */ }
        composable("photos") { /* PhotoLocationScreen() */ }
        composable("mlkit") { /* MLKitIntegrationScreen() */ }
        composable("gamification") { /* GamificationScreen() */ }
    }
}

@Composable
fun AccessScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp)
        ) {
            Text("Login", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = { navController.navigate("register") },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(50.dp)
        ) {
            Text("Registrati", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun MainMenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("TripTales", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = { navController.navigate("groups") }, Modifier.fillMaxWidth()) {
            Text("Gruppi di Gita")
        }

        Button(onClick = { navController.navigate("photos") }, Modifier.fillMaxWidth()) {
            Text("Foto e Luoghi")
        }

        Button(onClick = { navController.navigate("mlkit") }, Modifier.fillMaxWidth()) {
            Text("Funzioni ML Kit")
        }

        Button(onClick = { navController.navigate("gamification") }, Modifier.fillMaxWidth()) {
            Text("Badge e Gamification")
        }
    }
}
