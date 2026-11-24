package com.sgu.kampusgo.repository

import com.sgu.kampusgo.data.EventsUiState
import com.sgu.kampusgo.data.remote.EventApiClient

class EventRepository {
    suspend fun loadEvents(): EventsUiState = EventApiClient.loadEvents()
}