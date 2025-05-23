package com.example.pw_frontend_triptales

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pw_frontend_triptales.screens.GroupListScreen
import com.example.pw_frontend_triptales.ui.theme.PwfrontendtriptalesTheme

class MenuActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token", null)

        setContent {
            PwfrontendtriptalesTheme {
                if (accessToken != null) {
                    Scaffold(
                        topBar = {
                            TopAppBar(title = { Text("Menu Gita") })
                        }
                    ) { padding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            GroupListScreen(accessToken)

                            Spacer(modifier = Modifier.height(24.dp))

                            Button(onClick = {
                                with(sharedPreferences.edit()) {
                                    remove("access_token")
                                    remove("refresh_token")
                                    apply()
                                }
                                val intent = Intent(this@MenuActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }) {
                                Text("Logout")
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
