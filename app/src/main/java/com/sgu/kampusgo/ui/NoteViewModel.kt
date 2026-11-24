package com.sgu.kampusgo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sgu.kampusgo.data.local.Note
import com.sgu.kampusgo.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
class NoteViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = NoteRepository(app)
    // WhileSubscribed keeps the query alive across a rotation.
    val notes: StateFlow<List<Note>> = repository.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    fun add(title: String, body: String) {
        viewModelScope.launch {
            repository.insert(Note(title = title.trim(), body = body.trim()))
        }
    }
    fun delete(note: Note) {
        viewModelScope.launch { repository.delete(note) }
    }
}