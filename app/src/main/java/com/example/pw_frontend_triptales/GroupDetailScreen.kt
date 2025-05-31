package com.example.pw_frontend_triptales

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pw_frontend_triptales.models.Group
import com.example.pw_frontend_triptales.models.Post
import com.example.pw_frontend_triptales.ui.theme.InstaButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GroupDetailScreen(
    group: Group,
    onBack: () -> Unit,
    onAddPost: (Group) -> Unit
) {
    var posts by remember { mutableStateOf(group.posts.toMutableList()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var postTitle by remember { mutableStateOf(TextFieldValue()) }
    var postText by remember { mutableStateOf(TextFieldValue()) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(group.nome_gruppo, style = MaterialTheme.typography.headlineMedium)
        group.descrizione?.let {
            Text(it, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InstaButton(
                text = "Torna indietro",
                onClick = onBack,
                modifier = Modifier.weight(1f)
            )
            InstaButton(
                text = "Aggiungi Post",
                onClick = { showAddDialog = true },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Post del gruppo:", style = MaterialTheme.typography.titleMedium)

        if (posts.isEmpty()) {
            Text("Nessun post disponibile.", style = MaterialTheme.typography.bodySmall)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(posts) { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(post.titolo, style = MaterialTheme.typography.titleSmall)
                            post.testo?.let {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(it, style = MaterialTheme.typography.bodySmall)
                            }
                            post.immagine_url?.let { imageUrl ->
                                Spacer(modifier = Modifier.height(8.dp))
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "Immagine del post",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Creato il: ${post.data_creazione.take(10)}",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Nuovo Post") },
            text = {
                Column {
                    OutlinedTextField(
                        value = postTitle,
                        onValueChange = { postTitle = it },
                        label = { Text("Titolo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = postText,
                        onValueChange = { postText = it },
                        label = { Text("Testo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                InstaButton(
                    text = "Aggiungi",
                    onClick = {
                        if (postTitle.text.isNotBlank()) {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                            val newPost = Post(
                                id = posts.size + 1,
                                utente = 1,
                                gruppo = group.id,
                                titolo = postTitle.text,
                                testo = postText.text,
                                immagine_path = null,
                                data_creazione = LocalDateTime.now().format(formatter),
                                latitudine = null,
                                longitudine = null,
                                testo_OCR = null,
                                testo_tradotto = null,
                                tags_oggetti = null,
                                didascalia_automatica = null
                            )
                            posts = (posts + newPost).toMutableList()
                            onAddPost(group.copy(posts = posts))
                            postTitle = TextFieldValue()
                            postText = TextFieldValue()
                            showAddDialog = false
                        }
                    }
                )
            },
            dismissButton = {
                InstaButton(
                    text = "Annulla",
                    onClick = { showAddDialog = false }
                )
            }
        )
    }
}
