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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sgu.kampusgo.ui.NoteViewModel
@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = viewModel(),
) {
    val notes by viewModel.notes.collectAsState()
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Body") },
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = {
                viewModel.add(title, body)
                title = ""
                body = ""
            },
            enabled = title.isNotBlank(),
        ) { Text("Save") }
        if (notes.isEmpty()) {
            Text("No notes yet", modifier = Modifier.padding(top = 12.dp))
        } else {
            LazyColumn(Modifier.weight(1f)) {
                items(notes, key = { it.id }) { note ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(note.title)
                            Text(note.body)
                        }
                        TextButton(onClick = { viewModel.delete(note) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}