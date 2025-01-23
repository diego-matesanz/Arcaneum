package com.diego.matesanz.arcaneum.ui.screens.camera.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform

sealed interface CameraAction {
    data class BookScanned(val isbn: String) : CameraAction
}

class CameraViewModel(repository: BooksRepository) : ViewModel() {

    private val isbn = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<UiState> = isbn
        .filter { isbn -> isbn.isNotBlank() }
        .transform { isbn -> emit(repository.findBookByIsbn(isbn)) }
        .map { book -> UiState(book = book) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState(isLoading = true),
        )

    data class UiState(
        val isLoading: Boolean = false,
        val book: Book? = null,
        val isError: Boolean = false,
    )

    fun onAction(action: CameraAction) {
        when (action) {
            is CameraAction.BookScanned -> findBookByIsbn(action.isbn)
        }
    }

    private fun findBookByIsbn(isbn: String) {
        this.isbn.value = isbn
    }
}
