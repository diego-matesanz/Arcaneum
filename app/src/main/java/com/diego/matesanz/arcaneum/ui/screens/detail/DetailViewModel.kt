package com.diego.matesanz.arcaneum.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val id: String) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    private val repository = BooksRepository()

    init {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            state = state.copy(isLoading = false, book = repository.fetchBookById(id))
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val book: Book? = null
    )
}
