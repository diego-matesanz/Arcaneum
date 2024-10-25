package com.diego.matesanz.arcaneum.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    private val repository = BooksRepository()

    fun fetchBooksBySearch(search: String) {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            state = UiState(isLoading = false, books = repository.fetchBooksBySearchText(search))
        }
    }

    data class UiState(
        val books: List<Book> = emptyList(),
        val isLoading: Boolean = false,
    )
}
