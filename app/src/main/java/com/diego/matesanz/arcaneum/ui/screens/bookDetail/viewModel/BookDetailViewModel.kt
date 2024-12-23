package com.diego.matesanz.arcaneum.ui.screens.bookDetail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.Shelf
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface BookDetailAction {
    data class Bookmarked(val shelfId: Int, val book: Book) : BookDetailAction
    data class DominantColor(val color: Int) : BookDetailAction
}

class BookDetailViewModel(
    private val id: String,
    private val booksRepository: BooksRepository,
    private val shelvesRepository: ShelvesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    data class UiState(
        val isLoading: Boolean = false,
        val book: Book? = null,
        val dominantColor: Int = 0,
        val shelves: List<Shelf> = emptyList(),
    )

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            combine(booksRepository.findBookById(id), shelvesRepository.shelves) { book, shelves ->
                _state.update { it.copy(book = book, shelves = shelves, isLoading = false) }
            }.collect()
        }
    }

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.Bookmarked -> onBookmarked(action.shelfId, action.book)
            is BookDetailAction.DominantColor -> onDominantColor(action.color)
        }
    }

    private fun onDominantColor(color: Int) {
        _state.update { it.copy(dominantColor = color) }
    }

    private fun onBookmarked(shelfId: Int, book: Book) {
        viewModelScope.launch {
            if (book.shelfId == shelfId) {
                booksRepository.deleteBook(book.bookId)
            } else {
                booksRepository.saveBook(book.copy(shelfId = shelfId))
            }
        }
    }
}
