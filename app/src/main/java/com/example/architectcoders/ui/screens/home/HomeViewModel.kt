package com.example.architectcoders.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoders.data.Book
import com.example.architectcoders.data.BooksRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var state = mutableStateOf(UiState())
        private set

    private val repository = BooksRepository()

    fun onUiReady() {
        viewModelScope.launch {
            state.value = UiState(isLoading = true)
            state.value = UiState(isLoading = false, books = repository.fetchPopularBooks())
        }
    }

    data class UiState(
        val books: List<Book> = emptyList(),
        val isLoading: Boolean = false,
    )
}
