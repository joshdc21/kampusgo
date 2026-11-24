package com.sgu.kampusgo.repository

import android.content.Context
import com.sgu.kampusgo.data.local.Note
import com.sgu.kampusgo.data.local.NoteDatabase
import kotlinx.coroutines.flow.Flow
class NoteRepository(context: Context) {
    private val dao = NoteDatabase.get(context).noteDao()
    fun observeAll(): Flow<List<Note>> = dao.observeAll()
    suspend fun insert(note: Note) = dao.insert(note)
    suspend fun delete(note: Note) = dao.delete(note)
}