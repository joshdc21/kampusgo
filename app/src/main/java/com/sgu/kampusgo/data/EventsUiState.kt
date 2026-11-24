package com.sgu.kampusgo.data

import com.sgu.kampusgo.Event

sealed interface EventsUiState {
    data object Loading : EventsUiState
    data class Success(val events: List<Event>) : EventsUiState
    data class Error(val message: String) : EventsUiState
}