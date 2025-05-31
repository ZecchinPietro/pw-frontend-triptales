package com.example.pw_frontend_triptales

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.pw_frontend_triptales.models.Group
import com.example.pw_frontend_triptales.ui.theme.InstaButton

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GroupListScreen(accessToken: String) {
    val groups = remember { mutableStateListOf<Group>() }
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedGroup by remember { mutableStateOf<Group?>(null) }
    var groupName by remember { mutableStateOf(TextFieldValue()) }
    var groupDescription by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(Unit) {
        groups += Group(
            id = 1,
            nome_gruppo = "Esploratori Urbani",
            descrizione = "Un gruppo per esplorare la città e condividere scoperte.",
            codice_accesso = "123ABC",
            posts = listOf()
        )
    }

    if (selectedGroup == null) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                InstaButton(
                    text = "Aggiungi Gruppo",
                    onClick = { showAddDialog = true },
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
            }

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                items(groups) { group ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(group.nome_gruppo, style = MaterialTheme.typography.titleLarge)
                            group.descrizione?.let {
                                Text(it, style = MaterialTheme.typography.bodyMedium)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                InstaButton(
                                    text = "Visualizza",
                                    onClick = { selectedGroup = group },
                                    modifier = Modifier.weight(1f)
                                )
                                InstaButton(
                                    text = "Elimina",
                                    onClick = { groups.remove(group) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            if (showAddDialog) {
                AlertDialog(
                    onDismissRequest = { showAddDialog = false },
                    confirmButton = {
                        InstaButton(
                            text = "Crea",
                            onClick = {
                                if (groupName.text.isNotBlank()) {
                                    val newId = (groups.maxOfOrNull { it.id } ?: 0) + 1
                                    groups += Group(
                                        id = newId,
                                        nome_gruppo = groupName.text,
                                        descrizione = groupDescription.text,
                                        codice_accesso = "CODE$newId",
                                        posts = listOf()
                                    )
                                    groupName = TextFieldValue()
                                    groupDescription = TextFieldValue()
                                    showAddDialog = false
                                }
                            }
                        )
                    },
                    dismissButton = {
                        InstaButton(text = "Annulla", onClick = { showAddDialog = false })
                    },
                    title = { Text("Nuovo Gruppo") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = groupName,
                                onValueChange = { groupName = it },
                                label = { Text("Nome gruppo") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = groupDescription,
                                onValueChange = { groupDescription = it },
                                label = { Text("Descrizione") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                )
            }
        }
    } else {
        GroupDetailScreen(
            group = selectedGroup!!,
            onBack = { selectedGroup = null },
            onAddPost = { updatedGroup ->
                val index = groups.indexOfFirst { it.id == updatedGroup.id }
                if (index >= 0) {
                    groups[index] = updatedGroup
                    selectedGroup = updatedGroup
                }
            }
        )
    }
}
