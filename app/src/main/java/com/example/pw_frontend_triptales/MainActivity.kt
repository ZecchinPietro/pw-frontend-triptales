package com.example.pw_frontend_triptales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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

    }
}

@Composable
        Column(
        ) {
            Button(
                onClick = { navController.navigate("login") },
            ) {
            }

                onClick = { navController.navigate("register") },
            modifier = Modifier
        ) {
        }
    }
}

@Composable
    }
}
