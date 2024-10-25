package com.diego.matesanz.arcaneum.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
            try {
                state = UiState(isLoading = true, searchText = search, isError = false)
                state = UiState(
                    isLoading = false,
                    books = repository.fetchBooksBySearchText(search),
                )
            } catch (_: Exception) {
                state = UiState(isLoading = false, isError = true)
            }
        }
    }

    data class UiState(
        val books: List<Book> = emptyList(),
        val isLoading: Boolean = false,
        val searchText: String = "",
        val isError: Boolean = false,
    )
}
