package com.example.pw_frontend_triptales.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pw_frontend_triptales.RetrofitClient
import com.example.pw_frontend_triptales.models.Group
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun GroupListScreen(accessToken: String) {
    var groups by remember { mutableStateOf<List<Group>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        try {
            val authHeader = "Bearer $accessToken"
            groups = RetrofitClient.apiService.getGroups(authHeader)
        } catch (e: Exception) {
            Toast.makeText(context, "Errore nel caricamento dei gruppi", Toast.LENGTH_SHORT).show()
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            items(groups) { group ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = group.nome_gruppo, style = MaterialTheme.typography.titleLarge)
                        group.descrizione?.let {
                            Text(text = it, style = MaterialTheme.typography.bodyMedium)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Post del gruppo:", style = MaterialTheme.typography.titleMedium)

                        if (group.posts.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))

                            group.posts.forEach { post ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = post.titolo,
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                        post.testo?.let {
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(it, style = MaterialTheme.typography.bodySmall)
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Creato il: ${post.data_creazione.take(10)}", // solo YYYY-MM-DD
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }
                            }
                        } else {
                            Text(
                                "Nessun post disponibile per questo gruppo.",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}
