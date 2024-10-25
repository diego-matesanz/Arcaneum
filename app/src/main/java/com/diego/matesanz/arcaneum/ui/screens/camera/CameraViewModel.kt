package com.diego.matesanz.arcaneum.ui.screens.camera

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    private val repository = BooksRepository()

    fun fetchBookByIsbn(isbn: String) {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true, isError = false)
                state = state.copy(isLoading = false, book = repository.fetchBookByIsbn(isbn))
            } catch (_: Exception) {
                state = state.copy(isLoading = false, isError = true)
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val book: Book? = null,
        val isError: Boolean = false,
    )
}
