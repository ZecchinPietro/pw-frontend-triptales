package com.example.pw_frontend_triptales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pw_frontend_triptales.ui.theme.InstaButton
import android.content.Intent
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TripTalesApp()
            }
        }
    }
}

@Composable
fun TripTalesApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegistrationScreen(navController) }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Benvenuto su TripTales!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        InstaButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth(),
            text = "Login"
        )

        Spacer(modifier = Modifier.height(16.dp))

        InstaButton(
            onClick = { navController.navigate("register") },
            modifier = Modifier.fillMaxWidth(),
            text = "Registrati"
        )
        Button(
            onClick = {
                val intent = Intent(context, MenuActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Bypass Login (DEBUG)")
        }
    }
}

