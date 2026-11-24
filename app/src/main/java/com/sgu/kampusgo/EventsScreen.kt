package com.sgu.kampusgo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sgu.kampusgo.data.EventsUiState
import com.sgu.kampusgo.ui.EventViewModel
@Composable
fun EventsScreen(
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        when (val current = state) {
            EventsUiState.Loading -> {
                CircularProgressIndicator(Modifier.padding(24.dp))
            }
            is EventsUiState.Error -> {
                Text(current.message, modifier = Modifier.padding(24.dp))
                Button(onClick = { viewModel.refresh() }) { Text("Retry") }
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