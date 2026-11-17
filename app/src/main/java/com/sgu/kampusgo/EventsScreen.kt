package com.sgu.kampusgo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun EventsScreen(
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf<EventsUiState>(EventsUiState.Loading) }
    var reload by remember { mutableIntStateOf(0) }
    LaunchedEffect(reload) {
        state = EventsUiState.Loading
        state = EventApiClient.loadEvents()
    }
    Column(modifier = modifier.fillMaxSize()) {
        when (val current = state) {
            EventsUiState.Loading -> {
                CircularProgressIndicator(Modifier.padding(24.dp))
            }
            is EventsUiState.Error -> {
                Text(current.message, modifier = Modifier.padding(24.dp))
                Button(onClick = { reload += 1 }) { Text("Retry") }
            }
            is EventsUiState.Success -> {
                EventList(
                    events = current.events,
                    onEventClick = onEventClick,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}