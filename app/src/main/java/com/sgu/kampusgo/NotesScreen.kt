package com.sgu.kampusgo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val dao = remember(context) { NoteDatabase.get(context).noteDao() }
    val notes by dao.observeAll().collectAsState(initial = emptyList())
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize().padding(15.dp),
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = {
                Text("Title")
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = {
                Text("Title")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val note = Note(title = title.trim(), body = body.trim())
                scope.launch { dao.insert(note) }
                title = ""
                body = ""
            },
            enabled = title.isNotBlank()
        ) {
            Text("Save")
        }

        if (notes.isEmpty()) {
            Text(
                text = "No notes yet",
                modifier = Modifier.padding(12.dp)
            )
        } else {
            LazyColumn(Modifier.weight(1f)) {
                items(notes, key = { it.id }) { note ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = note.title)
                            Text(text = note.body)
                        }
                        TextButton(
                            onClick = {
                                scope.launch { dao.delete(note) }
                            }
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}
