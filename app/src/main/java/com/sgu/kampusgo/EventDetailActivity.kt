package com.sgu.kampusgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sgu.kampusgo.ui.theme.KampusGoTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val id = intent.getStringExtra("id") ?: ""
        setContent {
            KampusGoTheme {
                EventDetailScreen(id = id, onBack = { finish() })
            }
        }
    }
}

@Composable
fun EventDetailScreen(id: String, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val event = sampleEvents.find { it.id == id }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        TextButton(onClick = onBack) { Text("Back") }
        if (event == null) {
            Text("No event for this id")
        } else {
            Text(event.title)
            Text(event.category)
            Text(event.venue)
            Text(event.startsAt)
            Text(event.description, modifier = Modifier.padding(top = 12.dp))
        }
    }
}
